package org.jboss.errai.demo.client.local.usercomplaints;

import com.google.common.collect.ImmutableMultimap;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.databinding.client.api.InitialState;
import org.jboss.errai.demo.client.shared.UserComplaint;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.shared.api.annotations.*;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * @author edewit@redhat.com
 */
@Dependent
@Templated("search.html#item")
public class SearchResult extends Composite implements HasModel<UserComplaint> {
  @Inject
  private TransitionTo<View> view;

  @Inject
  @AutoBound
  private DataBinder<UserComplaint> dataBinder;

  @Inject
  @Bound
  @DataField
  private Label name;

  @Inject
  @Bound
  @DataField
  private Label email;

  @Override
  public UserComplaint getModel() {
    return dataBinder.getModel();
  }

  @Override
  public void setModel(UserComplaint userComplaint) {
    dataBinder.setModel(userComplaint, InitialState.FROM_MODEL);
  }

  @EventHandler
  public void onClick(ClickEvent e) {
    view.go(ImmutableMultimap.of("id", String.valueOf(dataBinder.getModel().getId())));
  }
}
