# Definition for singly-linked list.
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

class Solution:
    def removeNthFromEnd(self, head: ListNode, n: int) -> ListNode:
        """
        删除链表的倒数第 n 个节点，并返回链表的头节点
        
        时间复杂度: O(L)，其中 L 是链表的长度
        空间复杂度: O(1)
        """
        # 创建虚拟头节点，避免处理头节点被删除的情况
        dummy = ListNode(0)
        dummy.next = head
        
        # 初始化快慢指针
        fast = dummy
        slow = dummy
        
        # 快指针先走n步
        for _ in range(n):
            fast = fast.next
        
        # 快慢指针同时移动，直到快指针到达末尾
        while fast.next:
            fast = fast.next
            slow = slow.next
        
        # 删除倒数第n个节点
        slow.next = slow.next.next
        
        return dummy.next

# 示例用法
if __name__ == "__main__":
    # 创建链表: 1 -> 2 -> 3 -> 4 -> 5
    head = ListNode(1)
    head.next = ListNode(2)
    head.next.next = ListNode(3)
    head.next.next.next = ListNode(4)
    head.next.next.next.next = ListNode(5)
    
    solution = Solution()
    result = solution.removeNthFromEnd(head, 2)  # 删除倒数第2个节点
    
    # 打印结果
    current = result
    while current:
        print(current.val, end=" -> " if current.next else "")
        current = current.next
    print()  # 输出: 1 -> 2 -> 3 -> 5