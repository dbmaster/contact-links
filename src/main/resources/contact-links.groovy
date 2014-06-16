import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import com.branegy.inventory.api.ContactLinkService;
import com.branegy.inventory.model.Application;
import com.branegy.inventory.model.ContactLink;
import com.branegy.inventory.model.Server;
import com.branegy.service.base.api.ProjectService;

def toURL = { link -> link.encodeURL().replaceAll("\\+", "%20") }
String.metaClass.encodeURL = { java.net.URLEncoder.encode(delegate) }

List<ContactLink> contactLinks;

String projectName =  dbm.getService(ProjectService.class).getCurrentProject().getName();

ContactLinkService service = dbm.getService(ContactLinkService.class);

contactLinks = service.findAllByClass(Application.class,null)
contactLinks += service.findAllByClass(Server.class,null)

contactLinks.sort { it-> it.getContact().getContactName().toUpperCase() }

if (p_contact_name!=null && p_contact_name.length()>0) {
   p_contact_name = p_contact_name.toUpperCase()
   contactLinks = contactLinks.findAll { it.getContact().getContactName().toUpperCase().contains(p_contact_name) }
}

println """<table class="simple-table" cellspacing="0">
        <tr style="background-color:#EEE"><td>Contact</td><td colspan="2">Related to</td></tr>"""

contactLinks.each { link ->
    String objectName, objectType, objectLink, contactLink

    if (link.getApplication()!=null) {
    objectName = link.getApplication().getApplicationName();
    objectType = "Application"
        objectLink = "#inventory/project:${toURL(projectName)}/applications/application:${toURL(objectName)}/contacts"
    } else if (link.getServer()!=null) {
    objectName = link.getServer().getServerName();
    objectType = "Server"
        objectLink = "#inventory/project:${toURL(projectName)}/servers/server:${toURL(objectName)}/contacts"
    }

    contactName = link.getContact().getContactName();
    contactLink = "#inventory/project:${toURL(projectName)}/contacts/contact:${toURL(contactName)}"

    println """<tr>
                  <td><a href='${contactLink}'>${contactName}</a></td>
                  <td>${objectType}</td>
                  <td><a href='${objectLink}'>${objectName}</a></td>
               </tr> """;

}
println "</table>"
