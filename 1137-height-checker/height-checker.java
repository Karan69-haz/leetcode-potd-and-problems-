class Solution {
    public int heightChecker(int[] heights) {
        int[] res = new int[heights.length];

        for(int i=0; i<heights.length; i++){
            res[i] = heights[i];
        }

        for(int i=0; i<heights.length; i++){
            int j=i;

            while(j>0 && res[j-1] > res[j]){
                int temp = res[j-1];
                res[j-1] = res[j];
                res[j] = temp;
                j--;
            }
        }

        int count = 0;
        for(int i=0; i<res.length; i++){
            if(res[i] != heights[i]){
                count++;
            }
        }
        return count;
    }
}