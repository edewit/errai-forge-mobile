package org.jboss.errai.demo.client.local;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.demo.client.local.usercomplaints.Search;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.inject.Inject;

/**
 * @author edewit@redhat.com
 */
@Page(role = DefaultPage.class)
@Templated("#panel")
public class Welcome extends Composite {

  @Inject
  @DataField
  private TransitionAnchor<Search> userComplaints;

}
