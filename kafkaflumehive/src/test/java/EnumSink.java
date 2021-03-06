

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
import java.util.List;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.flume.hive.entity.EnumEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;


public class EnumSink extends AbstractSink implements Configurable {
	

	private Logger log = LoggerFactory.getLogger(EnumSink.class);
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

	public EnumSink() {
		log.debug("EnumSink Start...");
	}

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

	@Override
	public synchronized void start() {
		super.start();
		try {
			Class.forName("org.apache.hive.jdbc.HiveDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = "jdbc:hive2://" + metastore + ":" + port + "/" + database;
		try {
			conn = DriverManager.getConnection(url, "admin", "admin");
			// 创建一个Statement对象
			statement = conn.prepareStatement("insert into " + table
					+ " partition(dt=?)(line_name,station_name,key_id,source_id,code_type,code_id,record_time,value) values (?,?,?,?,?,?,?,?)");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

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

	@Override
	public Status process() throws EventDeliveryException {
		System.out.println("...........................");
		Status result = Status.READY;
        Channel channel = getChannel();
        Transaction transaction = channel.getTransaction();
        Event event;
        String content;
        transaction.begin();
        List<EnumEntity> infos = Lists.newArrayList();
		for (int i = 0; i < batchSize; i++) {
			event = channel.take(); // 从channel中获取一条数据
			log.debug(">>> " + i + "  : 数据传输开始... ");
			if (event != null) {
				content = new String(event.getBody());
				EnumEntity entity = JSON.parseObject(content, EnumEntity.class);
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
				for (EnumEntity entity : infos) {
					String lineTag = entity.getLineTag()!=null?entity.getLineTag():"";
					String regionTag = entity.getRegionTag()!=null?entity.getRegionTag():"";
					String major = entity.getMajor()!=null?entity.getMajor():"";
					String srcIdTag = entity.getSrcIdTag()!=null?entity.getSrcIdTag():"";
					String typeTag = entity.getTypeTag()!=null?entity.getTypeTag():"";
					String pointcodeTag = entity.getPointcodeTag()!=null?entity.getPointcodeTag():"";
					Long time = entity.getTimestamp()!=null?entity.getTimestamp():0;
					Integer value = entity.getValue()!=null?entity.getValue():0;
					//					System.out.println(entity);
					//					statement.setString(1, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
					//					System.out.println("=============1");
					//					statement.setString(2, entity.getLineTag());
					//					System.out.println("=============2");
					//					statement.setString(3, entity.getRegionTag());
					//					System.out.println("=============3");
					//					statement.setString(4, entity.getMajor()+"."+entity.getLineTag()+"."+entity.getRegionTag()+"."+entity.getSrcIdTag()+"."+entity.getTypeTag()+"."+entity.getPointcodeTag());
					//					System.out.println("=============4");
					//					statement.setInt(5, Integer.parseInt(entity.getSrcIdTag()));
					//					System.out.println("=============5");
					//					statement.setString(6, entity.getTypeTag());
					//					System.out.println("=============6");
					//					statement.setInt(7, Integer.parseInt(entity.getPointcodeTag()));
					//					System.out.println("=============7");
					//					statement.setTimestamp(8, new Timestamp(entity.getTimestamp()*1000));
					//					System.out.println("=============8");
					//					statement.setInt(9, entity.getValue());
					//					System.out.println("-----------------------------STATEM");
					//					statement.executeUpdate();
					System.out.println(entity);
					try {
						bw.write(lineTag+","+regionTag+","+major+"."+lineTag+"."+regionTag+"."+srcIdTag+"."+typeTag+"."+pointcodeTag+","+srcIdTag+","+typeTag+","+pointcodeTag+","+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time*1000)+","+value);
						bw.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
				bw.close();
				fos.close();
				System.out.println("...............写入完成...............");
			}
			transaction.commit();
		} catch (Exception e) {
			try {
				System.out.println("------------transaction.rollback()------------");
				transaction.rollback(); // 执行回滚操作
				
			} catch (Exception e2) {
				System.out.println("-------------Exception in rollback. Rollback might not have been" + "successful.");
			}
			System.out.println("---------------Failed to commit transaction." + "Transaction rolled back.");
			Throwables.propagate(e);
		}finally {
			transaction.close();
		}
		return result;
	}

}
