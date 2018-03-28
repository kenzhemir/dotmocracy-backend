import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Assylkhanov Aslan on 02.03.2018.03.2018=
 */
@ApplicationPath("/api")
public class DotmocracyApplication extends Application {

    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();

    @Override
    public Set <Class<?>> getClasses() {
        return empty;
    }
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

}
