import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LifeRuleTest {

	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	/*
	 * *
	 该测试案例是测试初始化细胞矩阵数组是否正确
	功能描述如下：
	1.实例化细胞规则对象时，调用构造函数
	2.函数要求参数为行数和列数，初始化细胞矩阵的大小为规定的行数和列数
	3.初始化该矩阵的所有位置的值为0
	 */
	public void testLifeRule() {
		LifeRule liferule = new LifeRule(3, 3);
		int [][] test = new int[liferule.getMaxRow()][liferule.getMaxCol()];
		test = liferule.getGrid();
		assertEquals(3,liferule.getMaxRow());
		assertEquals(3,liferule.getMaxCol());
		assertEquals(0,test[0][0]);
		assertEquals(0,test[0][1]);
		assertEquals(0,test[0][2]);
		assertEquals(0,test[1][0]);
		assertEquals(0,test[1][1]);
		assertEquals(0,test[1][2]);
		assertEquals(0,test[2][0]);
		assertEquals(0,test[2][1]);
		assertEquals(0,test[2][2]);
	}

	@Test
	public void testUpdateMap() {
		LifeRule liferule = new LifeRule(3, 3);
		int [][] test = new int[4][4];
		for (int i = 0; i <= 3; i++) {
			for (int j =0; j <= 3; j++) {
				test[i][j] = 0;	
		   }
		}
		test[1][1] = 1;
		test[1][2] = 1;
		test[1][3] = 1;
		test[2][1] = 1;
		//将信息保存到矩阵中
		liferule.setGrid(test);
		liferule.UpdateMap();
		int [][] grid = liferule.getGrid();
		assertEquals(0,grid[0][0]);
		assertEquals(0,grid[0][1]);
		assertEquals(1,grid[0][2]);
		assertEquals(0,grid[0][3]);
		assertEquals(0,grid[1][0]);
		assertEquals(1,grid[1][1]);
		assertEquals(1,grid[1][2]);
		assertEquals(0,grid[1][3]);
		assertEquals(0,grid[2][0]);
		assertEquals(1,grid[2][1]);
		assertEquals(0,grid[2][2]);
		assertEquals(0,grid[2][3]);
		assertEquals(0,grid[3][0]);
		assertEquals(0,grid[3][1]);
		assertEquals(0,grid[3][2]);
		assertEquals(0,grid[3][3]);
	}

	@Test
	/*
	 * *
	 该测试案例是测试每个细胞周围活细胞数目是否正常
	功能描述如下：
	1.用户在GUI界面让细胞开始繁衍
	2.获得每个细胞周围活细胞数目
	 */
	public void testNeighbors() {
		LifeRule liferule = new LifeRule(3, 3);
		int [][] test = new int[4][4];
		for (int i = 0; i <= 3; i++) {
			for (int j =0; j <= 3; j++) {
				test[i][j] = 0;	
		   }
		}
		test[1][1] = 1;
		test[1][2] = 1;
		test[1][3] = 1;
		test[2][1] = 1;
		//将信息保存到矩阵中
		liferule.setGrid(test);
		assertEquals(1,liferule.neighbors(0, 0));
		assertEquals(2,liferule.neighbors(0, 1));
		assertEquals(3,liferule.neighbors(0, 2));
		assertEquals(2,liferule.neighbors(0, 3));
		assertEquals(2,liferule.neighbors(1, 0));
		assertEquals(2,liferule.neighbors(1, 1));
		assertEquals(3,liferule.neighbors(1, 2));
		assertEquals(1,liferule.neighbors(1, 3));
		assertEquals(2,liferule.neighbors(2, 0));
		assertEquals(2,liferule.neighbors(2, 1));
		assertEquals(4,liferule.neighbors(2, 2));
		assertEquals(2,liferule.neighbors(2, 3));
		assertEquals(1,liferule.neighbors(3, 0));
		assertEquals(1,liferule.neighbors(3, 1));
		assertEquals(1,liferule.neighbors(3, 2));
		assertEquals(0,liferule.neighbors(3, 3));
		
	}

	@Test
	/*
	 * *
	 该测试案例是将细胞矩阵的状态设置为用户规定的状态。
	功能描述如下：
	1.用户在GUI界面选择活细胞的位置以及细胞矩阵的大小
	2.矩阵应储存规定的信息
	 */
	public void testSetGrid() {
		LifeRule liferule = new LifeRule(3, 3);
		int [][] test = new int[4][4];
		for (int i = 0; i <= 3; i++) {
			for (int j =0; j <= 3; j++) {
				test[i][j] = 0;	
		   }
		}
		test[1][1] = 1;
		test[1][2] = 1;
		test[1][3] = 1;
		test[2][1] = 1;
		//将信息保存到矩阵中
		liferule.setGrid(test);
		int [][] grid = liferule.getGrid();
		assertEquals(0,grid[0][0]);
		assertEquals(0,grid[0][1]);
		assertEquals(0,grid[0][2]);
		assertEquals(0,grid[0][3]);
		assertEquals(0,grid[1][0]);
		assertEquals(1,grid[1][1]);
		assertEquals(1,grid[1][2]);
		assertEquals(1,grid[1][3]);
		assertEquals(0,grid[2][0]);
		assertEquals(1,grid[2][1]);
		assertEquals(0,grid[2][2]);
		assertEquals(0,grid[2][3]);
		assertEquals(0,grid[3][0]);
		assertEquals(0,grid[3][1]);
		assertEquals(0,grid[3][2]);
		assertEquals(0,grid[3][3]);
		
	}

}
