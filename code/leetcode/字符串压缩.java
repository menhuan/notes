public class 字符串压缩 {
    
    public static void main(String[] args) {
        String s ="asdasdasda";
        int num = 1;
        char ch =s.charAt(0);
        StringBuilder builder = new StringBuilder();
        for(int index = 1; index <s.length();index++ ){
            if (ch == s.charAt(index)){
                num++;
            }else{
                builder.append(ch).append(num);
                num=1;
            }
        }
        builder.delete();
        if (builder.length()>s.length()){
            return s;
        }else{
            return builder.toString();
        }

    }
}