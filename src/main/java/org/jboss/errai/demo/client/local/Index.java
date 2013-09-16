package org.jboss.errai.demo.client.local;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import org.jboss.errai.enterprise.client.jaxrs.api.RestClient;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ui.nav.client.local.Navigation;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author edewit@redhat.com
 */
@EntryPoint
@Templated("template.html#container")
public class Index extends Composite {
  @Inject
  private Navigation navigation;

  @Inject
  @DataField("nav")
  private NavBar navbar;

  @Inject
  @DataField
  private Footer footer;

  @Inject
  @DataField
  private SimplePanel content;

  @PostConstruct
  private void init() {
    // This is specifying the relative path to the REST endpoint used to store
    // complaints on the server. When compiling the native mobile app of this
    // demo, this needs to be changed to an absolute URL.
    RestClient.setApplicationRoot("/errai-tutorial/rest");
    // RestClient.setApplicationRoot("http://10.15.16.207:8080/errai-tutorial/rest");
  }
}
