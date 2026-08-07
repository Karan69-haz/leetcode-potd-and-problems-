class Solution {
    public String decodeString(String s) {
        Stack<String>stack = new Stack<>();

        for(char ch : s.toCharArray()){
            if(ch == ']'){
                String ns = "";
                while(!stack.isEmpty() && !stack.peek().equals( "[")){
                    ns = stack.pop() + ns;

                }
                if(!stack.isEmpty()){
                    stack.pop();

                }
                String repcnt = "";
                while(!stack.isEmpty() && stack.peek().matches("\\d")){
                    repcnt = stack.pop() + repcnt;
                }
                int k = Integer.parseInt(repcnt);
                String decstr = "";
                for(int i = 0; i < k; i++){
                    decstr += ns;
                }
                stack.push(decstr);
            }
            else{
                //add to stack
                stack.push(String.valueOf(ch));
            }
        }
        String res = "";
        for(String tos : stack){
            res += tos;
        }
        return res;
        
    }
}