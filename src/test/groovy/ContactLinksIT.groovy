import io.dbmaster.testng.BaseToolTestNGCase

import static org.testng.Assert.assertTrue
import org.testng.annotations.Test

import com.branegy.tools.api.ExportType


public class ContactLinksIT extends BaseToolTestNGCase {

    @Test
    public void test() {
        def parameters = [ "p_contact_name"  :  "test" ]
        String result = tools.toolExecutor("dbmaster-contact-links", parameters).execute()
        assertTrue(result.contains("Contact"), "Unexpected search results ${result}");
        assertTrue(result.contains("Related to"), "Unexpected search results ${result}");
    }
}
