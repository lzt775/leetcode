# Definition for singly-linked list.
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

class Solution:
    def removeNthFromEnd(self, head: ListNode, n: int) -> ListNode:
        """
        删除链表的倒数第 n 个节点，并返回链表的头节点
        
        思路：使用双指针法
        1. 创建虚拟头节点，避免处理头节点被删除的情况
        2. 快指针先走n步
        3. 然后快慢指针同时移动，直到快指针到达末尾
        4. 此时慢指针指向要删除节点的前一个节点
        """
        # 创建虚拟头节点
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

def create_linked_list(values):
    """根据值列表创建链表"""
    if not values:
        return None
    
    head = ListNode(values[0])
    current = head
    for val in values[1:]:
        current.next = ListNode(val)
        current = current.next
    
    return head

def print_linked_list(head):
    """打印链表"""
    current = head
    values = []
    while current:
        values.append(str(current.val))
        current = current.next
    print(" -> ".join(values))

def test_remove_nth_from_end():
    """测试函数"""
    solution = Solution()
    
    # 测试用例1: [1,2,3,4,5], n=2
    print("测试用例1: [1,2,3,4,5], n=2")
    head1 = create_linked_list([1, 2, 3, 4, 5])
    print("原链表:", end=" ")
    print_linked_list(head1)
    result1 = solution.removeNthFromEnd(head1, 2)
    print("删除倒数第2个节点后:", end=" ")
    print_linked_list(result1)
    print()
    
    # 测试用例2: [1], n=1
    print("测试用例2: [1], n=1")
    head2 = create_linked_list([1])
    print("原链表:", end=" ")
    print_linked_list(head2)
    result2 = solution.removeNthFromEnd(head2, 1)
    print("删除倒数第1个节点后:", end=" ")
    print_linked_list(result2)
    print()
    
    # 测试用例3: [1,2], n=1
    print("测试用例3: [1,2], n=1")
    head3 = create_linked_list([1, 2])
    print("原链表:", end=" ")
    print_linked_list(head3)
    result3 = solution.removeNthFromEnd(head3, 1)
    print("删除倒数第1个节点后:", end=" ")
    print_linked_list(result3)
    print()
    
    # 测试用例4: [1,2,3], n=3
    print("测试用例4: [1,2,3], n=3")
    head4 = create_linked_list([1, 2, 3])
    print("原链表:", end=" ")
    print_linked_list(head4)
    result4 = solution.removeNthFromEnd(head4, 3)
    print("删除倒数第3个节点后:", end=" ")
    print_linked_list(result4)

if __name__ == "__main__":
    test_remove_nth_from_end()