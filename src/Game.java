import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;;

/**
 * 细胞游戏的GUI界面. 根据LifeRule类的设计 以二维矩阵存储细胞的位置信息，则使用网格布局来作为细胞的位置矩阵
 * 用户可以自定义细胞的位置，则每个网格都可放置一个按钮供用户来选择 用户可自定义细胞矩阵的大小，则使用下拉菜单控件来进行选择
 * 细胞可以持续繁殖，则使用线程睡眠机制
 * 
 * @author Sunny
 *
 */
public class Game extends JFrame implements ActionListener {

	private static Game frame; // 游戏对象 frame
	private JPanel backPanel, centerPanel, bottomPanel; // 游戏中的面板布局
	private JButton btnOK, btnNextGeneration, btnStart, btnStop, btnExit;
	private JButton[][] btnBlock; // 每个网格对应的按钮所形成的按钮数组
	private int maxRow, maxCol; // 用户选择的行数和列数
	private LifeRule life; // 调用LifeRule类对游戏界面进行逻辑控制
	private JComboBox rowList, colList; // 选择行数和列数的列表
	private boolean[][] isSelected; // 保存每个按钮所代表细胞的存活状态
	private JLabel lblRow, lblCol;
	private boolean isRunning; // 对游戏进行暂停，继续的控制按钮
	private Thread thread; // 使用线程来进行细胞的不间断演化
	private boolean isDead;

	public Game(String string) {
		super(string);
		initMap();
	}

	/**
	 * 得到行数.
	 * 
	 * @return 行数
	 */
	public int getMaxRow() {
		return maxRow;
	}

	/**
	 * 设置行数.
	 * 
	 * @param maxRow
	 */
	public void setMaxRow(int maxRow) {
		this.maxRow = maxRow;
	}

	/**
	 * 得到列数.
	 * 
	 * @return 列数
	 */
	public int getMaxCol() {
		return maxCol;
	}

	/**
	 * 设置列数.
	 * 
	 * @param maxCol
	 */
	public void setMaxCol(int maxCol) {
		this.maxCol = maxCol;
	}

	public static void main(String[] args) {
		frame = new Game("生命游戏");
	}

	/**
	 * 生成GUI截图界面.
	 */
	public void initMap() {
		/**
		 * 设计地图生成器界面 .
		 */
		if (maxRow == 0) {
			maxRow = 20;
		}

		if (maxCol == 0) {
			maxCol = 40;
		}
		// 初始化游戏控制类
		life = new LifeRule(maxRow, maxCol);

		backPanel = new JPanel(new BorderLayout());
		centerPanel = new JPanel(new GridLayout(maxRow, maxCol));
		bottomPanel = new JPanel();

		rowList = new JComboBox();
		// 设置下拉列表的选择项信息
		for (int i = 3; i <= 20; i++) {
			rowList.addItem(String.valueOf(i));
		}
		colList = new JComboBox();
		for (int j = 3; j <= 40; j++) {
			colList.addItem(String.valueOf(j));
		}
		// 最小的细胞规模设置为一个九宫格，则下拉列表选则3，则选中位置的下标为0
		// 设置下拉列表的默认选择项为最大值maxRow-3，maxCol-3
		rowList.setSelectedIndex(maxRow - 3);
		colList.setSelectedIndex(maxCol - 3);

		// 设置按钮信息
		btnOK = new JButton("确定");
		btnNextGeneration = new JButton("下一代");
		btnBlock = new JButton[maxRow][maxCol];
		btnStart = new JButton("开始繁衍");
		btnStop = new JButton("停止繁衍");
		btnExit = new JButton("退出");
		isSelected = new boolean[maxRow][maxCol];
		lblRow = new JLabel("设置行数：");
		lblCol = new JLabel("设置列数：");

		// 设置panel容器为JFrame的内容面板
		this.setContentPane(backPanel);
		backPanel.add(centerPanel, "Center");
		backPanel.add(bottomPanel, "South");

		// 设置每个细胞的位置为一个按钮
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxCol; j++) {
				btnBlock[i][j] = new JButton("");
				btnBlock[i][j].setBackground(Color.WHITE);
				centerPanel.add(btnBlock[i][j]);
			}
		}

		bottomPanel.add(lblRow);
		bottomPanel.add(rowList);
		bottomPanel.add(lblCol);
		bottomPanel.add(colList);
		bottomPanel.add(btnOK);
		bottomPanel.add(btnNextGeneration);
		bottomPanel.add(btnStart);
		bottomPanel.add(btnStop);
		bottomPanel.add(btnExit);

		// 设置窗口
		this.setSize(900, 650);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		// 将窗口设置为可见的
		this.setVisible(true);

		// 注册监听器

		btnOK.addActionListener(this);
		btnNextGeneration.addActionListener(this);
		btnStart.addActionListener(this);
		btnStop.addActionListener(this);
		btnExit.addActionListener(this);
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxCol; j++) {
				btnBlock[i][j].addActionListener(this);
			}
		}

	}

	@Override
	/**
	 * 事件响应函数.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOK) {
			// 重新选择细胞地图的大小时则根据选取到的值在列表选值数组中的位置来设定细胞地图的maxRow和maxCol并更新地图
			frame.setMaxRow(rowList.getSelectedIndex() + 3);
			frame.setMaxCol(colList.getSelectedIndex() + 3);
			initMap();
		} else if (e.getSource() == btnNextGeneration) {
			// 选择逐代繁殖则调用逐代繁殖的函数
			makeNextGeneration();
		} else if (e.getSource() == btnStart) {
			// 选择不间断繁殖则使用线程睡眠的方法
			isRunning = true;
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (isRunning) {
						makeNextGeneration();
						try {
							// 每隔0.5S就演化一次
							Thread.sleep(500);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						// isDead用于判断地图是否还可再进行下一次繁殖，如果为true，则全部细胞死亡无法进行演化。
						isDead = true;
						// 遍历所有点，看是否还有存活的细胞
						for (int row = 0; row < maxRow; row++) {
							for (int col = 0; col < maxCol; col++) {
								if (life.getGrid()[row][col] != 0) {
									isDead = false;
									break;
								}
							}
							if (!isDead) {
								break;
							}
						}
						if (isDead) {
							JOptionPane.showMessageDialog(null, "细胞全部死亡，无法进行繁殖了！");
							isRunning = false;
							thread = null;
						}
					}
				}
			});
			// 启动线程
			thread.start();
		} else if (e.getSource() == btnStop) {
			// 暂停游戏并使该线程结束
			isRunning = false;
			thread = null;
		} else if (e.getSource() == btnExit) {
			System.exit(0);
		} else {
			// 选择活细胞的位置
			// 调用游戏控制类的细胞原始状态
			int[][] grid = life.getGrid();
			// 遍历所有网格，并确定玩家是否点击了该位置，点击后则将该位置的状态变为原来的反状态
			for (int i = 0; i < maxRow; i++) {
				for (int j = 0; j < maxCol; j++) {
					if (e.getSource() == btnBlock[i][j]) {
						isSelected[i][j] = !isSelected[i][j];
						if (isSelected[i][j]) {
							btnBlock[i][j].setBackground(Color.BLACK);
							grid[i][j] = 1;
						} else {
							btnBlock[i][j].setBackground(Color.WHITE);
							grid[i][j] = 0;
						}
						break;
					}
				}
			}
			// 将用户设定的细胞状态信息保存到游戏控制类中
			life.setGrid(grid);
		}
	}

	/**
	 * 更新细胞的位置矩阵的信息并改变相应位置的颜色.
	 */
	private void makeNextGeneration() {
		// 逐步演化则先根据细胞存活的规则更新一次地图
		life.UpdateMap();
		int[][] grid = life.getGrid();
		// 根据下一代的地图，遍历所有点，并根据存活状态更新它们的颜色状态
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxCol; j++) {
				if (grid[i][j] == 1) {
					btnBlock[i][j].setBackground(Color.BLACK);
				} else {
					btnBlock[i][j].setBackground(Color.WHITE);
				}
			}
		}

	}

}
