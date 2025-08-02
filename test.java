public class test {
    
    /**
     * Definition for singly-linked list.
     */
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    /**
     * 无重复字符的最长子串
     * 使用滑动窗口算法
     * 时间复杂度: O(n)
     * 空间复杂度: O(min(m, n)) 其中m是字符集大小
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int n = s.length();
        int maxLength = 0;
        int left = 0;
        
        // 使用数组记录字符最后出现的位置
        int[] charIndex = new int[128]; // ASCII字符集
        for (int i = 0; i < 128; i++) {
            charIndex[i] = -1;
        }
        
        for (int right = 0; right < n; right++) {
            char currentChar = s.charAt(right);
            
            // 如果字符已经出现过，更新左边界
            if (charIndex[currentChar] >= left) {
                left = charIndex[currentChar] + 1;
            }
            
            // 更新字符位置
            charIndex[currentChar] = right;
            
            // 计算当前窗口长度
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * 删除排序链表中的重复元素 II
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     */
    public static ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(0, head);
        ListNode cur = dummy;
        while(cur.next != null && cur.next.next != null){
            int x = cur.next.val;
            if(cur.next.val == cur.next.next.val){
                while(cur.next != null && cur.next.val == x){
                    cur.next = cur.next.next;
                }
            }else{
                cur = cur.next;
            }
        }
        return dummy.next;
    }
    
    /**
     * 删除链表的倒数第 N 个结点
     * 使用双指针法
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode fast = dummy;
        ListNode slow = dummy;
        
        // 快指针先走n步
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }
        
        // 快慢指针同时移动
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        
        // 删除倒数第n个节点
        slow.next = slow.next.next;
        
        return dummy.next;
    }
    
    /**
     * 测试方法
     */
    public static void testLengthOfLongestSubstring() {
        System.out.println("=== 无重复字符的最长子串测试 ===");
        
        // 测试用例1: "abcabcbb" -> 3
        String test1 = "abcabcbb";
        int result1 = lengthOfLongestSubstring(test1);
        System.out.println("测试用例1: s = \"" + test1 + "\"");
        System.out.println("期望输出: 3");
        System.out.println("实际输出: " + result1);
        System.out.println("测试结果: " + (result1 == 3 ? "✓ 通过" : "✗ 失败"));
        System.out.println();
        
        // 测试用例2: "bbbbb" -> 1
        String test2 = "bbbbb";
        int result2 = lengthOfLongestSubstring(test2);
        System.out.println("测试用例2: s = \"" + test2 + "\"");
        System.out.println("期望输出: 1");
        System.out.println("实际输出: " + result2);
        System.out.println("测试结果: " + (result2 == 1 ? "✓ 通过" : "✗ 失败"));
        System.out.println();
        
        // 测试用例3: "pwwkew" -> 3
        String test3 = "pwwkew";
        int result3 = lengthOfLongestSubstring(test3);
        System.out.println("测试用例3: s = \"" + test3 + "\"");
        System.out.println("期望输出: 3");
        System.out.println("实际输出: " + result3);
        System.out.println("测试结果: " + (result3 == 3 ? "✓ 通过" : "✗ 失败"));
        System.out.println();
    }
    
    /**
     * 主方法
     */
    public static void main(String[] args) {
        testLengthOfLongestSubstring();
    }
}
