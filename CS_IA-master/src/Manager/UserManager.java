package Manager;

import AccountBookGUI.InterfaceManager;
import Models.UserModel;


public class UserManager {
    public static int findUser(String id) {
        int index = 0;
        for (UserModel user : InterfaceManager.userList) {
            if (user.getId().equals(id)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static boolean isMatched(String id, String password) {
        int index = findUser(id);
        if (index > -1) {
            if (InterfaceManager.userList.get(index).getPassword().equals(password)) {
                UserModel user = InterfaceManager.userList.get(index);
                InterfaceManager.sessionUtil.login(user.getUsername(), id, password, user.getTotalIncome(), user.getTotalExpenditure(), user.getCurrency());
                return true;
            }
        }
        return false;
    }
}
