package org.jboss.errai.demo.client.local.usercomplaints;

import com.google.common.collect.ImmutableMultimap;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.databinding.client.api.InitialState;
import org.jboss.errai.demo.client.shared.UserComplaint;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.shared.api.annotations.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author edewit@redhat.com
 */
@Templated("#app-template")
@Page
public class View extends Composite {

  @Inject
  private EntityManager em;

  @Inject
  @AutoBound
  private DataBinder<UserComplaint> dataBinder;

  @PageState("id")
  private Long requestedId;

  @Inject
  @Bound
  @DataField
  private Label name;

  @Inject
  @Bound
  @DataField
  private Label complaint;

  @Inject
  private TransitionTo<Search> backPage;

  @Inject
  private TransitionTo<Form> editPage;

  @Inject
  @DataField
  private Button all;

  @Inject
  @DataField
  private Button edit;

  @Inject
  @DataField
  private Button create;

  @PageShown
  private void setup() {
    if (requestedId != null) {
      UserComplaint found = em.find(UserComplaint.class, requestedId);

      if (found != null) {
        dataBinder.setModel(found, InitialState.FROM_MODEL);
      } else {
        Window.alert("No such entity: " + requestedId);
        backPage.go();
      }
    }
  }

  @EventHandler("all")
  private void onAll(ClickEvent e) {
    backPage.go();
  }

  @EventHandler("edit")
  private void onEdit(ClickEvent e) {
    editPage.go(ImmutableMultimap.of("id", String.valueOf(dataBinder.getModel().getId())));
  }

  @EventHandler("create")
  private void onCreate(ClickEvent e) {
    editPage.go();
  }

}
