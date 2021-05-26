import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * @lc app=leetcode.cn id=212 lang=java
 *
 * [212] 单词搜索 II
 */

// @lc code=start
class Solution {
    class TrieNode {
        HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
        // 代表结束后的字符是什么
        String word = null;

        public TrieNode() {
        }
    }

    char[][] _board = null;
    ArrayList<String> _result = new ArrayList<String>();

    public List<String> findWords(char[][] board, String[] words) {
        // 使用前缀树，那么就需要构建前缀树
        TrieNode trie = new TrieNode();
        for (String word : words) {
            TrieNode node = trie;
            // 将每个字符放到前缀数据离那
            for (Character character : word.toCharArray()) {
                if (node.children.containsKey(character)) {
                    node = node.children.get(character);
                } else {
                    TrieNode newNode = new TrieNode();
                    node.children.put(character, newNode);
                    node = newNode;
                }
            }
            node.word = word; // 存储到此到前缀树中
        }

        // 开始查询从board中查询数据
        this._board = board;
        for (int row = 0; row < board.length; ++row) {
            for (int column = 0; column < board[row].length; ++column) {
                if (trie.children.containsKey(board[row][column])) {
                    // 递归
                    backtracking(row, column, trie);
                }
            }
        }

        return this._result;

    }

    private void backtracking(int row, int col, TrieNode parent) {
        Character letter = this._board[row][col];
        TrieNode currentNode = parent.children.get(letter);
        // 检查是否匹配上
        if (currentNode.word != null) {
            this._result.add(currentNode.word);
            // 匹配上就把这个置为null
            currentNode.word = null;
        }

        // 查找过的字符串置为一个结束字符
        this._board[row][col] = '#';

        // 开始进行查找 四边通
        int[] rowOffset = { -1, 0, 1, 0 };
        int[] colOffset = { 0, 1, 0, -1 };
        for (int i = 0; i < 4; i++) {
            int newx = row + rowOffset[i], newy = col + colOffset[i];
            // 控制范围
            if (newx < 0 || newx >= this._board.length || newy < 0 || newy >= this._board[0].length) {
                continue;
            }
            if (currentNode.children.containsKey(this._board[newx][newy])) {
                backtracking(newx, newy, currentNode);
            }
        }
        // 进行回溯回去
        this._board[row][col] = letter;

        // 前缀树 子节点为空那么就移除 匹配过的就不再进行匹配
        if (currentNode.children.isEmpty()) {
            currentNode.children.remove(letter);
        }

    }

}

// @lc code=end
