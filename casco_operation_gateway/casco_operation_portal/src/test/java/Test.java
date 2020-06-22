import com.casco.operationportal.CascoOperationPortalApplition;
import com.casco.operationportal.controller.SysTLineController;
import com.casco.operationportal.entity.SysTLine;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CascoOperationPortalApplition.class)
public class Test {

	@Before
	public void init() {
		System.out.println("开始测试...");
	}

	@After
	public void after() {
		System.out.println("测试结束...");
	}

	@Autowired
	SysTLineController sysTLineController;

	@org.junit.Test
	public void add(){
		SysTLine sysTLine = new SysTLine();
		sysTLine.setLineName("linaName");
		sysTLine.setLineCode("lineCode");
		sysTLineController.add(sysTLine);
	}

	@org.junit.Test
	public void get(){
		sysTLineController.get("0fb3a01401840bce3bc7c8e361a70d25");
	}

	@org.junit.Test
	public void update(){
		SysTLine sysTLine = new SysTLine();
		sysTLine.setUuid("0fb3a01401840bce3bc7c8e361a70d25");
		sysTLine.setLineName("linaName111");
		sysTLine.setLineCode("lineCode111");
		sysTLineController.update(sysTLine);
	}

	@org.junit.Test
	public void delete(){
		sysTLineController.delete("0fb3a01401840bce3bc7c8e361a70d25");
	}

	@org.junit.Test
	public void list(){
		sysTLineController.list((long)0,(long)10);
	}
}
