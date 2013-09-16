package org.jboss.errai.demo.client.local;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.demo.client.local.usercomplaints.Search;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.inject.Inject;

/**
 * @author edewit@redhat.com
 */
@Templated("template.html#nav")
public class NavBar extends Composite {

  @Inject
  @DataField
  private TransitionAnchor<Search> userComplaints;

  @EventHandler("userComplaints")
  private void onUserComplaints(ClickEvent e) {
    userComplaints.addStyleName("active");
    userComplaints.click();
  }
}
