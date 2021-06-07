import java.util.HashMap;
import java.util.Map;

/*
 * @lc app=leetcode.cn id=146 lang=java
 *
 * [146] LRU 缓存机制
 */

// @lc code=start
class LRUCache {

    // 定义一个链表类
    class DlinkedNode {
        int key;
        int value;
        // 进行上一个链接
        DlinkedNode prev;
        DlinkedNode next;

        // 构造函数
        public DlinkedNode() {
        }

        public DlinkedNode(int _key, int _value) {
            key = _key;
            value = _value;

        }

    }

    // 进行查询到o1
    private Map<Integer, DlinkedNode> cache = new HashMap<Integer, DlinkedNode>();
    // 缓存的存储数据的大小
    private int size;

    // 容量大小
    private int capactity;
    // 空节点
    private DlinkedNode head, tail;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capactity = capacity;
        // 伪造 伪头部和尾部及诶单那
        head = new DlinkedNode();
        tail = new DlinkedNode();
        head.next = tail;
        tail.prev = head;

    }

    public int get(int key) {
        // 获取一个数据
        DlinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        // 如果key存在，先通过hash定位，再移到头部
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        // 放置node就需要考虑多个情况了。
        DlinkedNode node = cache.get(key);
        // 存在于不存在两种情况。
        if (node == null) {
            DlinkedNode cur = new DlinkedNode(key, value);
            // 添加到hash表上
            cache.put(key, cur);
            // 添加到头部
            addToHead(cur);
            ++size;
            // 大于了capacitity
            if (size > capactity) {
                DlinkedNode tail1 = removeTail();
                cache.remove(tail1.key);
                --size;
            }
        } else {
            // key存在
            node.value = value;
            // 移动到头部
            moveToHead(node);
        }

    }

    private DlinkedNode removeTail() {
        // tail 一直是空的 用来表示尾指针。找到最后一个数据
        DlinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }

    private void addToHead(DlinkedNode node) {
        // 将接地那移到头部
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void moveToHead(DlinkedNode node) {
        // 移除node
        removeNode(node);
        // 然后迁移到前面
        addToHead(node);
    }

    private void removeNode(DlinkedNode node) {
        // 移除节点 需要修改前一个节点的next 以及后一个节点的PREV
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such: LRUCache obj =
 * new LRUCache(capacity); int param_1 = obj.get(key); obj.put(key,value);
 */
// @lc code=end
