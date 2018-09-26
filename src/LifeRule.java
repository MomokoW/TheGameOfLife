/*].
游ri戏解读：
 某一细胞的邻居包
 括上、下、左、右、左上、左下、右上与右下相邻之细胞，游戏规则如下：
 孤单死亡：如果细胞的邻居小于一个，则该细胞在下一次状态将死亡。
 拥挤死亡：如果细胞的邻居在四个以上，则该细胞在下一次状态将死亡。
 稳定：如果细胞的邻居为二个或三个，则下一次状态为稳定存活。
 复活：如果某位置原无细胞存活，而该位置的邻居为三个，则该位置将复活一细胞。
 解法生命游戏的规则可简化为以下，并使用CASE比对即可使用程式实作：
 邻居个数为0、1、4、5、6、7、8时，则该细胞下次状态为死亡。
 邻居个数为2时，则该细胞下次状态为复活。
 邻居个数为3时，则该细胞下次状态为稳定
 */

/**
 * 用于规定细胞生存规则以及繁殖演化的类 使用二维矩阵来存储细胞的位置信息 每一个细胞所在位置的值代表细胞的生存状态 0为死亡，1为存活.
 * 
 * @author Sunny
 */
public class LifeRule {

	private int maxRow; // 细胞矩阵的行数
	private int maxCol; // 细胞矩阵的列数
	private int[][] grid; // 存储生命细胞位置的二维矩阵

	/**
	 * 初始化细胞位置矩阵，规定其大小，并初始化所有细胞为死细胞.
	 * 
	 * @param row
	 *            行数
	 * @param col
	 *            列数
	 */
	public LifeRule(int row, int col) {
		maxRow = row;
		maxCol = col;

		grid = new int[maxRow][maxCol];
		// 初始化所有位置的细胞都为死细胞
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxCol; j++) {
				grid[i][j] = 0;
			}
		}
	}

	/**
	 * 更新细胞的位置矩阵的信息.
	 */
	public void UpdateMap() {

		int[][] newGrid = new int[maxRow][maxCol]; // 创建新数组来保存当前代细胞的信息
		for (int row = 0; row < maxRow; row++) {
			for (int col = 0; col < maxCol; col++) {
				switch (neighbors(row, col)) { // 根据细胞周围细胞的存活数目来修改细胞的状态信息
				case 2:
					newGrid[row][col] = grid[row][col]; // 周围细胞数为2保持原来的状态
					break;
				case 3:
					newGrid[row][col] = 1; // 周围细胞数为3则存活
					break;
				default:
					newGrid[row][col] = 0; // 其余细胞数则死亡
					break;
				}
			}
		}
		for (int row = 0; row < maxRow; row++) {
			// 将繁殖后的结果保存到当前代中
			for (int col = 0; col < maxCol; col++) {
				grid[row][col] = newGrid[row][col];
			}
		}
	}

	/**
	 * 遍历以当前细胞为中心的九宫格，确定细胞周围活细胞数目.
	 * 
	 * @param row
	 *            当前细胞所在的行数
	 * @param col
	 *            当前细胞所在的列数
	 * @return 当前细胞周围存活的细胞的数目
	 */
	public int neighbors(int row, int col) {
		int count = 0, c, r;
		for (r = row - 1; r <= row + 1; r++) {
			for (c = col - 1; c <= col + 1; c++) {
				if (r < 0 || r >= maxRow || c < 0 || c >= maxCol) {
					continue;
				}
				if (grid[r][c] == 1) {
					count++;
				}
			}
		}
		if (grid[row][col] == 1) {
			count--;
		}
		return count;

	}

	/**
	 * 获取细胞位置矩阵的信息.
	 * 
	 * @return 位置信息
	 */
	public int[][] getGrid() {
		return grid;
	}

	/**
	 * 根据用户的选择设定细胞位置矩阵.
	 * 
	 * @param grid
	 */
	public void setGrid(int[][] grid) {
		this.grid = grid;
		this.setMaxRow(grid.length);
		this.setMaxCol(grid[0].length);
	}

	public int getMaxRow() {
		return maxRow;
	}

	public void setMaxRow(int maxRow) {
		this.maxRow = maxRow;
	}

	public int getMaxCol() {
		return maxCol;
	}

	public void setMaxCol(int maxCol) {
		this.maxCol = maxCol;
	}
}
