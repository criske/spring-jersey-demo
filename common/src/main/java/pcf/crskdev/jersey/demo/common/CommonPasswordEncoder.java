package pcf.crskdev.jersey.demo.common;

@FunctionalInterface
public interface CommonPasswordEncoder {

    String encode(final CharSequence password);
}
