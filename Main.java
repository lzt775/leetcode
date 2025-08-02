/**
 * Definition for singly-linked list.
 */
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {
    /**
     * 删除链表的倒数第 n 个节点，并返回链表的头节点
     * 
     * 时间复杂度: O(L)，其中 L 是链表的长度
     * 空间复杂度: O(1)
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 创建虚拟头节点
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // 初始化快慢指针
        ListNode fast = dummy;
        ListNode slow = dummy;
        
        // 快指针先走n步
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        
        // 快慢指针同时移动，直到快指针到达末尾
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        
        // 删除倒数第n个节点
        slow.next = slow.next.next;
        
        return dummy.next;
    }
}

// 示例用法
public class Main {
    public static void main(String[] args) {
        // 创建链表: 1 -> 2 -> 3 -> 4 -> 5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        
        Solution solution = new Solution();
        ListNode result = solution.removeNthFromEnd(head, 2); // 删除倒数第2个节点
        
        // 打印结果
        ListNode current = result;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println(); // 输出: 1 -> 2 -> 3 -> 5
    }
}