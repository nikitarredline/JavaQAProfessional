package components.popups;

import commons.AbsCommon;

public class AuthPopup extends AbsCommon implements IPopup<AuthPopup> {

    @Override
    public AuthPopup popupShouldNotBeVisible() {
        return null;
    }

    @Override
    public AuthPopup popupShouldBeVisible() {
        return null;
    }
}
