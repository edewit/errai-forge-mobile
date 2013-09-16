package org.jboss.errai.demo.client.local;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.inject.Inject;

/**
 * @author edewit@redhat.com
 */
@Templated("template.html#footer")
public class Footer extends Composite {

  @Inject
  @DataField
  private Button add;


}
