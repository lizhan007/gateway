package org.flume.hive.sink;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.apache.hive.service.cli.HiveSQLException;
import org.flume.hive.entity.DigitEntity;
import org.flume.hive.entity.EnumEntity;
import org.flume.hive.util.KeysUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;


public class BASDigitSink extends AbstractSink implements Configurable {


	private Logger log = LoggerFactory.getLogger(BASDigitSink.class);
	private String metastore;
	private String port;
	private String database;
	private String table;
	private String txnsPerBatchAsk;
	private int batchSize;
	private String filepath;
	private PreparedStatement statement;
	private Connection conn;
	private static ResultSet res = null;

	public BASDigitSink() {

	}

	/**
	 * Flume程序自动读取启动命令上的配置文件,并在当前方法内进行赋值
	 */
	@Override
	public void configure(Context context) {
		metastore = context.getString("metastore");
		Preconditions.checkNotNull(metastore, "metastore must be set!!");
		port = context.getString("port");
		Preconditions.checkNotNull(port, "port must be set!!");
		database = context.getString("database");
		Preconditions.checkNotNull(database, "database must be set!!");
		table = context.getString("table");
		Preconditions.checkNotNull(table, "table must be set!!");
		filepath = context.getString("filepath");
		Preconditions.checkNotNull(filepath, "filepath must be set!!");
		batchSize = context.getInteger("batchSize");
		Preconditions.checkNotNull(batchSize > 0, "batchSize must be a positive number!!");
	}

	/**
	 * 程序启动时进行初始化操作
	 */
	@Override
	public synchronized void start() {
		super.start();
		getConnection();
	}

	/**
	 * 获取或者刷新连接
	 */
	private void getConnection() {
		try {
			Class.forName("org.apache.hive.jdbc.HiveDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = "jdbc:hive2://" + metastore + ":" + port + "/" + database;
		try {
			conn = DriverManager.getConnection(url, "admin", "admin");
			// 创建一个Statement对象
			statement = conn.prepareStatement("load data local inpath '"+filepath+table+".txt' into table "+table+" partition(dt=?)");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 程序关闭是资源释放
	 */
	@Override
	public synchronized void stop() {
		super.stop();
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * 从channel通道内获取kafka中数据,并写入Hive中
	 */
	@Override
	public Status process() throws EventDeliveryException {
		Status result = Status.READY;
		Channel channel = getChannel();
		Transaction transaction = channel.getTransaction();
		Event event;
		String content;
		transaction.begin();
		List<DigitEntity> infos = Lists.newArrayList();
		for (int i = 0; i < batchSize; i++) {
			event = channel.take(); // 从channel中获取一条数据
			if (event != null) {
				content = new String(event.getBody());
				DigitEntity entity = JSON.parseObject(content, DigitEntity.class);
				infos.add(entity);
			} else {
				result = Status.BACKOFF;
				break;
			}
		}
		try {
			if (infos.size() > 0) {
				File fout = new File(filepath + table + ".txt");
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(fout);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
				for (DigitEntity entity : infos) {
					if(entity.getPointcodeTag()==null) {
						continue;
					}
					String lineTag = entity.getLineTag()!=null?entity.getLineTag():"";
					String regionTag = entity.getRegionTag()!=null?entity.getRegionTag():"";
					String major = entity.getMajor()!=null?entity.getMajor():"";
					String srcIdTag = entity.getSrcIdTag()!=null?entity.getSrcIdTag():"";
					String typeTag = entity.getTypeTag()!=null?entity.getTypeTag():"";
					String pointcodeTag = entity.getPointcodeTag()!=null?entity.getPointcodeTag():"";
					Long time = entity.getTimestamp()!=null?entity.getTimestamp():0;
					Integer value = entity.getValue()!=null?entity.getValue():0;

					try {
						bw.write(lineTag+","+regionTag+","+srcIdTag+","+KeysUtil.getISCSKey(entity)+","+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time*1000)+","+value);
						bw.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				bw.close();
				fos.close();
				statement.setString(1, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				statement.executeUpdate();
			}
			transaction.commit();
		}catch (Exception e) {
			try {
				transaction.rollback(); // 执行回滚操作
				getConnection();
				System.out.println(">>>BD Time Out");
			} catch (Exception e2) {
				System.out.println("-------------Exception in rollback. Rollback might not have been" + "successful.");
			}
			Throwables.propagate(e);
		}finally {
			transaction.close();
		}
		return result;
	}



}
