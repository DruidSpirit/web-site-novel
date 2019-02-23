package other;




import java.util.*;

public class Test {
    public static void main(String[] args) {
        // Cache
        //   DefaultWebSessionManager
       // Session
//        String name = ((ConsumerInterface) () ->"fsd" ).testname();
        ConsumerInterface consumerInterface = (
                        (ConsumerInterface) () -> "dsf"

                        );
        // System.out.println(name);
//        for ( int i =0;i<200;i++) {
//            System.out.println(UUID.randomUUID().getMostSignificantBits());
//        }

    }

    public static List<String> testname (Enum<?>...ens) {
        List<String> list = new ArrayList<>();
        for (Enum<?> en : ens) {
            list.add(en.name());
        }
        return list;
    }
    public interface ConsumerInterface{
       public String tedsfds();
    }

}
