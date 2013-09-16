package org.jboss.errai.demo.client.local.usercomplaints;


import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.databinding.client.api.InitialState;
import org.jboss.errai.demo.client.shared.UserComplaint;
import org.jboss.errai.demo.client.shared.UserComplaintEndpoint;
import org.jboss.errai.enterprise.client.jaxrs.api.ResponseCallback;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.shared.api.annotations.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author edewit@redhat.com
 */
@Templated("create.html#app-template")
@Page
public class Form extends Composite {

  @Inject
  private EntityManager em;

  @PageState("id")
  private Long requestedId;

  @Inject
  @AutoBound
  private DataBinder<UserComplaint> dataBinder;

  @Inject
  @Bound
  @DataField
  private TextBox name;

  @Inject
  @Bound
  @DataField
  private TextBox email;

  @Inject
  @Bound
  @DataField
  private TextArea complaint;

  @Inject
  @DataField
  private Button submit;

  @Inject
  @DataField
  private Button cancel;

  @Inject
  @DataField
  private Button delete;

  @DataField
  private Element error = DOM.createDiv();

  @Inject
  private Caller<UserComplaintEndpoint> endpoint;

  @Inject
  private TransitionTo<Search> searchPage;

  @PostConstruct
  private void init() {
    error.getStyle().setDisplay(Style.Display.NONE);
  }

  @PageShown
  private void setup() {
    if (requestedId != null) {
      UserComplaint found = em.find(UserComplaint.class, requestedId);

      if (found != null) {
        dataBinder.setModel(found, InitialState.FROM_MODEL);
        delete.setVisible(false);
      }
    }
    delete.setVisible(true);
  }

  @EventHandler("submit")
  private void onSubmit(ClickEvent e) {
    if (requestedId != null) {
      endpoint.call(new ResponseCallback() {
        @Override
        public void callback(Response response) {
          searchPage.go();
        }
      }).create(dataBinder.getModel());
    } else {
      em.merge(dataBinder.getModel());
      em.flush();
      searchPage.go();
    }
  }

  @EventHandler("cancel")
  private void onCancel(ClickEvent e) {
    searchPage.go();
  }

  @EventHandler("delete")
  private void onDelete(ClickEvent e) {
    endpoint.call(new RemoteCallback() {
      @Override
      public void callback(Object o) {
        searchPage.go();
      }
    }).delete(dataBinder.getModel().getId());
  }
}