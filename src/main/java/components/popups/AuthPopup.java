package components.popups;

import com.google.inject.Inject;
import commons.AbsCommon;
import support.GuiceScoped;
import waiters.Waiter;

public class AuthPopup extends AbsCommon implements IPopup<AuthPopup> {

    @Inject
    public AuthPopup(GuiceScoped guiceScoped, Waiter waiter) {
        super(guiceScoped, waiter);
    }

    @Override
    public AuthPopup popupShouldNotBeVisible() {
        return null;
    }

    @Override
    public AuthPopup popupShouldBeVisible() {
        return null;
    }
}
