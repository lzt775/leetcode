class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

class Solution:
    def maxPathSum(self, root):
        """
        计算二叉树的最大路径和
        
        Args:
            root: 二叉树的根节点
            
        Returns:
            int: 最大路径和
        """
        # 使用一个列表来存储全局最大值，因为我们需要在递归过程中更新它
        self.max_sum = float('-inf')
        
        def max_gain(node):
            """
            计算以当前节点为根的子树的单边最大路径和
            同时更新全局最大路径和
            
            Args:
                node: 当前节点
                
            Returns:
                int: 以当前节点为起点的单边最大路径和
            """
            if not node:
                return 0
            
            # 递归计算左右子树的单边最大路径和
            left_gain = max(max_gain(node.left), 0)
            right_gain = max(max_gain(node.right), 0)
            
            # 计算包含当前节点的路径和（可以包含左右子树）
            price_newpath = node.val + left_gain + right_gain
            
            # 更新全局最大路径和
            self.max_sum = max(self.max_sum, price_newpath)
            
            # 返回以当前节点为起点的单边最大路径和
            # 注意：这里只能选择左子树或右子树中的一个，不能同时选择两个
            return node.val + max(left_gain, right_gain)
        
        max_gain(root)
        return self.max_sum

def build_tree_from_list(values):
    """
    从列表构建二叉树
    
    Args:
        values: 列表形式的二叉树表示
        
    Returns:
        TreeNode: 二叉树的根节点
    """
    if not values:
        return None
    
    root = TreeNode(values[0])
    queue = [root]
    i = 1
    
    while queue and i < len(values):
        node = queue.pop(0)
        
        # 添加左子节点
        if i < len(values) and values[i] is not None:
            node.left = TreeNode(values[i])
            queue.append(node.left)
        i += 1
        
        # 添加右子节点
        if i < len(values) and values[i] is not None:
            node.right = TreeNode(values[i])
            queue.append(node.right)
        i += 1
    
    return root

def print_tree(root, level=0, prefix="Root: "):
    """
    打印二叉树结构（用于调试）
    """
    if root is None:
        return
    
    print("  " * level + prefix + str(root.val))
    if root.left:
        print_tree(root.left, level + 1, "L--- ")
    if root.right:
        print_tree(root.right, level + 1, "R--- ")

# 测试用例
if __name__ == "__main__":
    # 示例 1: [1,2,3]
    print("示例 1:")
    values1 = [1, 2, 3]
    root1 = build_tree_from_list(values1)
    print("输入:", values1)
    print("二叉树结构:")
    print_tree(root1)
    
    solution = Solution()
    result1 = solution.maxPathSum(root1)
    print("输出:", result1)
    print("解释: 最优路径是 2 -> 1 -> 3，路径和为 2 + 1 + 3 = 6")
    print()
    
    # 更多测试用例
    print("更多测试用例:")
    
    # 测试用例 2: [-10,9,20,None,None,15,7]
    values2 = [-10, 9, 20, None, None, 15, 7]
    root2 = build_tree_from_list(values2)
    print("输入:", values2)
    result2 = solution.maxPathSum(root2)
    print("输出:", result2)
    print()
    
    # 测试用例 3: [2,-1]
    values3 = [2, -1]
    root3 = build_tree_from_list(values3)
    print("输入:", values3)
    result3 = solution.maxPathSum(root3)
    print("输出:", result3)
    print()
    
    # 测试用例 4: [1]
    values4 = [1]
    root4 = build_tree_from_list(values4)
    print("输入:", values4)
    result4 = solution.maxPathSum(root4)
    print("输出:", result4)