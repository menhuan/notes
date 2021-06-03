/*
 * @lc app=leetcode.cn id=130 lang=java
 *
 * [130] 被围绕的区域
 */

// @lc code=start
class Solution {
    class UnionFind {
        // 孤立的集合有多少个
        int count;
        int[] parents;

        /**
         * 数据初始化 每个节点的数据等于坐标值
         * 
         * @param totalNodes
         */
        public UnionFind(int totalNodes) {
            count = totalNodes;
            parents = new int[totalNodes];
            for (int index = 0; index < totalNodes; index++) {
                parents[index] = index;

            }
        }

        public int find(int find) {
            while (find != parents[find]) {
                parents[find] = parents[parents[find]];
                find = parents[find];
            }
            return find;
        }

        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ)
                return;
            parents[rootP] = rootQ;
            count--;
        }

        // 检查是否连接在一起，连接在一起的话 他们的父节点是一样的
        public boolean isConnected(int p, int q) {
            return find(p) == find(q);
        }
    }

    public void solve(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }
        int rows = board.length;
        int cols = board[0].length;

        // 用一个虚拟节点, 边界上的O 的父节点都是这个虚拟节点
        UnionFind uf = new UnionFind(rows * cols + 1);
        int dummyNode = rows * cols;
        // 上下左右
        int[][] d = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // 等于他的时候才会进行合并操作
                if (board[row][col] == 'O') {
                    // 因为这里跟边界相连的O是不能被替换掉的
                    if (row == 0 || row == rows - 1 || col == 0 || col == cols - 1) {
                        // 将边界的O 全部与虚拟节点连接起来
                        uf.union(row * cols + col, dummyNode);
                    } else {
                        // 进行内部循环点的合并
                        for (int index = 0; index < d.length; index++) {
                            int x = row + d[index][0];
                            int y = col + d[index][1];

                            if (board[x][y] == 'O') {
                                uf.union(x * cols + y, row * cols + col);
                            }

                        }
                    }

                }
            }
        }

        for (int row = 0; row < rows - 1; row++) {
            for (int col = 0; col < cols - 1; col++) {
                if (!uf.isConnected(row * cols + col, dummyNode))
                    board[row][col] = 'X';
            }
        }
    }
}
// @lc code=end
