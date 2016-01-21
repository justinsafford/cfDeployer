import com.justinsafford.cfUtilities.Application;
import com.justinsafford.cfUtilities.cloudClient.CloudClientEntity;
import com.justinsafford.cfUtilities.cloudClient.CloudClientRepository;
import com.justinsafford.cfUtilities.cloudClient.CloudClientRequest;
import com.justinsafford.cfUtilities.cloudClient.CloudController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.URL;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ActiveProfiles("test")
@SpringApplicationConfiguration(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class CreateCloudClient {
    @Autowired
    CloudClientRepository cloudClientRepository;

    @Autowired
    CloudController cloudController;

    @Autowired
    CloudClientEntity testCloudCreds;


    @Before
    public void wipeDatabase() {
        cloudClientRepository.deleteAll();
    }

    //TODO:Test this with MockMVC
    @Test
    public void createNewCloudFoundryClient() throws Exception {
        CloudClientRequest cloudClientRequest = new CloudClientRequest();
        cloudClientRequest.setCloudFoundryUsername(testCloudCreds.getCloudUser());
        cloudClientRequest.setCloudFoundryPassword(testCloudCreds.getCloudPass());
        cloudClientRequest.setCloudFoundryOrg(testCloudCreds.getCloudOrg());
        cloudClientRequest.setCloudFoundrySpace(testCloudCreds.getCloudSpace());

        cloudController.createCloudFoundryClient(cloudClientRequest);

        List<CloudClientEntity> cloudClientsListEntity = cloudClientRepository.findAll();
        assertThat(cloudClientsListEntity.size(), is(1));
        CloudClientEntity actualCloudClientEntity = cloudClientsListEntity.get(0);
        assertThat(actualCloudClientEntity.getCloudUser(), is(testCloudCreds.getCloudUser()));
        assertThat(actualCloudClientEntity.getCloudPass(), is(testCloudCreds.getCloudPass()));
        assertThat(actualCloudClientEntity.getCloudOrg(), is(testCloudCreds.getCloudOrg()));
        assertThat(actualCloudClientEntity.getCloudSpace(), is(testCloudCreds.getCloudSpace()));

        URL url = new URL("HTTP", "api.run.pivotal.io", 80, "");
        assertThat(actualCloudClientEntity.getCloudUrl(), is(url));
    }
}
