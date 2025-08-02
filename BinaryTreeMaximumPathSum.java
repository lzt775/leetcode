/**
 * 二叉树最大路径和
 * 
 * 题目描述：在二叉树中，路径被定义为一条节点序列，序列中每对相邻节点之间都存在一条边，
 * 同一个节点在一条路径序列中至多出现一次，该路径至少包含一个节点，且不一定经过根节点。
 * 路径和是路径中各节点值的总和。给定一个二叉树的根节点root，要求返回其最大路径和。
 * 
 * 示例1：
 * 输入：root = [1,2,3]
 * 输出：6
 * 解释：最优路径是2 -> 1 -> 3，路径和为2 + 1 + 3 = 6
 */

// 定义二叉树节点
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class BinaryTreeMaximumPathSum {
    
    private int maxSum = Integer.MIN_VALUE;
    
    /**
     * 计算二叉树的最大路径和
     * @param root 二叉树的根节点
     * @return 最大路径和
     */
    public int maxPathSum(TreeNode root) {
        maxSum = Integer.MIN_VALUE;
        maxGain(root);
        return maxSum;
    }
    
    /**
     * 递归计算以当前节点为根的最大贡献值
     * @param node 当前节点
     * @return 以当前节点为根的最大贡献值
     */
    private int maxGain(TreeNode node) {
        if (node == null) {
            return 0;
        }
        
        // 递归计算左子树的最大贡献值
        int leftGain = Math.max(maxGain(node.left), 0);
        
        // 递归计算右子树的最大贡献值
        int rightGain = Math.max(maxGain(node.right), 0);
        
        // 计算以当前节点为根的新路径和
        int priceNewPath = node.val + leftGain + rightGain;
        
        // 更新全局最大路径和
        maxSum = Math.max(maxSum, priceNewPath);
        
        // 返回以当前节点为根的最大贡献值（只能选择左子树或右子树之一）
        return node.val + Math.max(leftGain, rightGain);
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        BinaryTreeMaximumPathSum solution = new BinaryTreeMaximumPathSum();
        
        // 测试示例1: [1,2,3]
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        
        System.out.println("示例1 - 输入: [1,2,3]");
        System.out.println("输出: " + solution.maxPathSum(root1));
        System.out.println("期望: 6");
        System.out.println();
        
        // 测试示例2: [-10,9,20,null,null,15,7]
        TreeNode root2 = new TreeNode(-10);
        root2.left = new TreeNode(9);
        root2.right = new TreeNode(20);
        root2.right.left = new TreeNode(15);
        root2.right.right = new TreeNode(7);
        
        System.out.println("示例2 - 输入: [-10,9,20,null,null,15,7]");
        System.out.println("输出: " + solution.maxPathSum(root2));
        System.out.println("期望: 42");
        System.out.println();
        
        // 测试示例3: [2,-1]
        TreeNode root3 = new TreeNode(2);
        root3.left = new TreeNode(-1);
        
        System.out.println("示例3 - 输入: [2,-1]");
        System.out.println("输出: " + solution.maxPathSum(root3));
        System.out.println("期望: 2");
        System.out.println();
        
        // 测试示例4: [1]
        TreeNode root4 = new TreeNode(1);
        
        System.out.println("示例4 - 输入: [1]");
        System.out.println("输出: " + solution.maxPathSum(root4));
        System.out.println("期望: 1");
    }
}