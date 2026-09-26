/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public boolean isSymmetric(TreeNode root) {
        Queue<TreeNode> leftqueue = new LinkedList<>();
        Queue<TreeNode> rightqueue = new LinkedList<>();

        leftqueue.add(root.left);
        rightqueue.add(root.right);

        while(!leftqueue.isEmpty() && !rightqueue.isEmpty()){
            TreeNode leftnode = leftqueue.poll();
            TreeNode rightnode = rightqueue.poll();
            if(leftnode == null && rightnode == null){
                continue;
            }
            if(leftnode == null || rightnode == null){
                return false;
            }
            if(leftnode.val != rightnode.val){
                return false;
            }
            leftqueue.add(leftnode.left);
            leftqueue.add(leftnode.right);
            rightqueue.add(rightnode.right);
            rightqueue.add(rightnode.left);


        
        }
        return false || true;
        
    }
}