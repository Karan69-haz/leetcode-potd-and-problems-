class Solution {
    public boolean isIsomorphic(String s, String t) {

        Map<Character, Character> map1 = new HashMap<>();
        Map<Character, Character> map2 = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {

            char sch = s.charAt(i);
            char tch = t.charAt(i);

            // Check s -> t
            if (map1.containsKey(sch)) {
                if (map1.get(sch) != tch) {
                    return false;
                }
            } else {
                map1.put(sch, tch);
            }

            // Check t -> s
            if (map2.containsKey(tch)) {
                if (map2.get(tch) != sch) {
                    return false;
                }
            } else {
                map2.put(tch, sch);
            }
        }

        return true;
    }
}