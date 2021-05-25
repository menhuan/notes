/*
 * @lc app=leetcode.cn id=208 lang=java
 *
 * [208] 实现 Trie (前缀树)
 */

// @lc code=start
class Trie {
    private Trie[] childres;
    private Boolean isEnd;

    /** Initialize your data structure here. */
    public Trie() {
        childres = new Trie[26];
        isEnd = false;

    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        // 插入一个单词到前缀树中
        Trie trie = this;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);

            // 检查下索引的位置是否存在字符
            int index = ch - 'a';
            if (trie.childres[index] == null) {
                trie.childres[index] = new Trie();
            }
            // 继续往下一个节点下面放入
            trie = trie.childres[index];
        }
        trie.isEnd = true;

    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        //
        Trie node = searchPrefix(word);
        return node != null && node.isEnd;
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        Trie node = searchPrefix(prefix);
        return node != null;

    }

    private Trie searchPrefix(String word) {
        Trie node = this;
        for (int j = 0; j < word.length(); j++) {
            char ch = word.charAt(j);
            int index = ch - 'a';
            if (node.childres[index] == null) {
                return null;
            }
            node = node.childres[index];
        }
        return node;
    }
}

/**
 * Your Trie object will be instantiated and called as such: Trie obj = new
 * Trie(); obj.insert(word); boolean param_2 = obj.search(word); boolean param_3
 * = obj.startsWith(prefix);
 */
// @lc code=end
