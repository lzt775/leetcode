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
     * 思路：使用双指针法
     * 1. 创建虚拟头节点，避免处理头节点被删除的情况
     * 2. 快指针先走n步
     * 3. 然后快慢指针同时移动，直到快指针到达末尾
     * 4. 此时慢指针指向要删除节点的前一个节点
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

public class RemoveNthFromEnd {
    
    /**
     * 根据数组创建链表
     */
    public static ListNode createLinkedList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }
        return head;
    }
    
    /**
     * 打印链表
     */
    public static void printLinkedList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println();
    }
    
    /**
     * 测试函数
     */
    public static void testRemoveNthFromEnd() {
        Solution solution = new Solution();
        
        // 测试用例1: [1,2,3,4,5], n=2
        System.out.println("测试用例1: [1,2,3,4,5], n=2");
        ListNode head1 = createLinkedList(new int[]{1, 2, 3, 4, 5});
        System.out.print("原链表: ");
        printLinkedList(head1);
        ListNode result1 = solution.removeNthFromEnd(head1, 2);
        System.out.print("删除倒数第2个节点后: ");
        printLinkedList(result1);
        System.out.println();
        
        // 测试用例2: [1], n=1
        System.out.println("测试用例2: [1], n=1");
        ListNode head2 = createLinkedList(new int[]{1});
        System.out.print("原链表: ");
        printLinkedList(head2);
        ListNode result2 = solution.removeNthFromEnd(head2, 1);
        System.out.print("删除倒数第1个节点后: ");
        printLinkedList(result2);
        System.out.println();
        
        // 测试用例3: [1,2], n=1
        System.out.println("测试用例3: [1,2], n=1");
        ListNode head3 = createLinkedList(new int[]{1, 2});
        System.out.print("原链表: ");
        printLinkedList(head3);
        ListNode result3 = solution.removeNthFromEnd(head3, 1);
        System.out.print("删除倒数第1个节点后: ");
        printLinkedList(result3);
        System.out.println();
        
        // 测试用例4: [1,2,3], n=3
        System.out.println("测试用例4: [1,2,3], n=3");
        ListNode head4 = createLinkedList(new int[]{1, 2, 3});
        System.out.print("原链表: ");
        printLinkedList(head4);
        ListNode result4 = solution.removeNthFromEnd(head4, 3);
        System.out.print("删除倒数第3个节点后: ");
        printLinkedList(result4);
    }
    
    public static void main(String[] args) {
        testRemoveNthFromEnd();
    }
}