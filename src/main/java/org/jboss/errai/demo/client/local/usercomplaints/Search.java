package org.jboss.errai.demo.client.local.usercomplaints;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.common.client.util.LogUtil;
import org.jboss.errai.demo.client.local.App;
import org.jboss.errai.demo.client.shared.UserComplaint;
import org.jboss.errai.demo.client.shared.UserComplaintEndpoint;
import org.jboss.errai.jpa.sync.client.local.ClientSyncManager;
import org.jboss.errai.jpa.sync.client.shared.SyncResponse;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.client.widget.UnOrderedList;
import org.jboss.errai.ui.cordova.events.OnlineEvent;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author edewit@redhat.com
 */
@Templated("search.html#app-template")
@Page
public class Search extends Composite {

  @Inject
  private EntityManager em;

  @Inject
  private App app;

  @Inject
  private ClientSyncManager syncManager;

  @Inject
  @DataField
  private TextBox search;

  @Inject
  @DataField
  private Button searchButton;

  @Inject
  @DataField
  @UnOrderedList
  private ListWidget<UserComplaint, SearchResult> results;

  @Inject
  private Caller<UserComplaintEndpoint> endpoint;


  private void loadComplaints() {
    TypedQuery<UserComplaint> query = em.createNamedQuery("allComplaints", UserComplaint.class);
    results.setItems(query.getResultList());
  }

  @PageShown
  private void sync() {
    loadComplaints();

    app.sync(new RemoteCallback<List<SyncResponse<UserComplaint>>>() {
      @Override
      public void callback(List<SyncResponse<UserComplaint>> response) {
        LogUtil.log("Received sync response:" + response);
        loadComplaints();
      }
    });
  }

  @EventHandler("searchButton")
  private void onSearch(ClickEvent e) {
    endpoint.call(new RemoteCallback<List<UserComplaint>>() {
      @Override
      public void callback(List<UserComplaint> userComplaints) {
        results.setItems(userComplaints);
      }
    }).search(search.getValue());
  }

  private void complaintChanged(@Observes UserComplaint created) {
    mergeInLocalStorage(created);
    loadComplaints();
  }

  public void mergeInLocalStorage(UserComplaint userComplaint) {
    syncManager.getExpectedStateEm().merge(userComplaint);
    syncManager.getExpectedStateEm().flush();
    syncManager.getDesiredStateEm().merge(userComplaint);
    syncManager.getDesiredStateEm().flush();
  }

  private void online(@Observes OnlineEvent onlineEvent) {
    sync();
  }
}
