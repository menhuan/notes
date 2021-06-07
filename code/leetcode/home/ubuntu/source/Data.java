public class Data {

    public static void main(String[] args) {
        int a = 101;
        twoToTen(a);
    }

    /**
     * 10进制转为2进制
     * 
     * @param n
     */
    public static void tenTo2(int n) {
        // StringBuffer 是线程安全的， StringBuuilder是线程不安全的
        StringBuilder builder = new StringBuilder();

        while (n > 0) {
            int res = n % 2; // 取余2作为二进制数
            builder.insert(0, res);
            n = n / 2;
        }
        System.out.println(builder);
    }

    /**
     * 2进制转为10进制
     * 
     * @param n
     * @throws NumberFormatException
     */
    public static void twoToTen(int n) {
        StringBuilder builder = new StringBuilder();
        String nStr = n + "";
        int len = nStr.length();
        int sum = 0;
        for (int index = 0; index < len; index++) {
            System.out.println(nStr.substring(index, index + 1));
            int value = Integer.parseInt(nStr.substring(index, index + 1));
            sum = sum * 10 + value;

        }
        System.out.println(sum);
    }

}
