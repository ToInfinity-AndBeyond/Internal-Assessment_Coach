package Utils;

import AccountBookGUI.InterfaceManager;
import Models.HistoryModel;
import Models.UserModel;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class FileUtil {
    public static void createDirectory() {
        String path = null;
        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book";
        }
        File Folder = new File(path);

        if (!Folder.exists()) {
            try{
                Folder.mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }
    }

    public static String findUser(String username, String id) {
        String path = null;
        BufferedReader reader;
        String str;

        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/user.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\user.data";
        }

        try {
            reader = new BufferedReader(new FileReader(path));

            while ((str = reader.readLine()) != null) {
                String[] divUser = str.split("\\|");
                if (Objects.equals(divUser[0], username) && Objects.equals(divUser[1], id)) {
                    return divUser[2];
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void saveUserList() {
        ArrayList<String> dataList = new ArrayList<>();
        String path = null;
        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/user.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\user.data";
        }

        for (int i = 0; i < InterfaceManager.userList.size(); i++) {
            UserModel user = InterfaceManager.userList.get(i);
            String line = user.getUsername() + "|" + user.getId() + "|" + user.getPassword() + "|" + user.getTotalExpenditure() + "|" + user.getTotalIncome() + "|" + user.getCurrency();
            dataList.add(line);
        }
        fileWriter(dataList, path);
    }

    public static void saveTotalMonetary() {
        BufferedReader reader;
        FileWriter fw;
        String dummy = "";

        String path = null;

        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/user.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\user.data";
        }

        try {
            reader = new BufferedReader(new FileReader(path));
            String str;

            while ((str = reader.readLine()) != null) {
                String[] userinfo = str.split("\\|");

                if (Objects.equals(userinfo[1], InterfaceManager.sessionUtil.getId())) {
                    str = userinfo[0] + "|" + userinfo[1] + "|" + userinfo[2] + "|" + InterfaceManager.sessionUtil.getTotalIncome() + "|" + InterfaceManager.sessionUtil.getTotalExpenditure() + "|" + userinfo[5];
                    dummy += (str + "\r\n");
                } else {
                    dummy += (str + "\r\n");
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            fw = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(dummy);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createUserFile() {
        String path = null;
        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/user.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\user.data";
        }
        File f = new File(path);

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void createMonthlyFile() {
        String path = null; //폴더 경로
        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_monthly.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_monthly.data";
        }
        File f = new File(path);

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void createHistoryFile() {
        String path = null;
        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_history.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_history.data";
        }
        File f = new File(path);

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void createCategoryFile() {
        String path = null;
        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_category.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_category.data";
        }
        ArrayList<String> dataList = new ArrayList<>();
        File f = new File(path);

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            dataList.add("Income|Salary|Allowance|");
            dataList.add("Expenditure|Food|Shopping|Medical|Hobby|Insurance|Transportation|");
            fileWriter(dataList, path);
        }
    }

    public static void saveHistory() {
        String path = null;
        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_history.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_history.data";
        }

        ArrayList<String> dataList = new ArrayList<>();

        for (int i = 0; i < InterfaceManager.userHistory.size(); i++) {
            HistoryModel history = (HistoryModel) InterfaceManager.userHistory.get(i);
            String line = history.getType() + "|" + history.getAmount() + "|" + history.getDate() +"|" + history.getCategory() + "|" + history.getPayment_method() + "|" + history.getAdd_type() + "|" + history.getNote();
            dataList.add(line);
        }
        fileWriter(dataList, path);
    }

    public static void getMonthlyData() {
        String path = null;

        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_monthly.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_monthly.data";
        }

        File file = new File(path);
        String str;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            while ((str = reader.readLine()) != null) {
                InterfaceManager.monthlyList.add(str);
                String[] dateInfo = str.split("\\|");
                InterfaceManager.monthlyDateList.add(dateInfo[2]);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveMonthlyData() {
        String path = null;

        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_monthly.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_monthly.data";
        }

        File file = new File(path);
        String dummy = "";
        FileWriter fw;
        String str;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            while ((str = reader.readLine()) != null) {
                dummy += str + "\r\n";
            }
            HistoryModel history = (HistoryModel) InterfaceManager.userHistory.get(InterfaceManager.userHistory.size() - 1);
            String date = history.getDate();
            String[] splitDate = date.split("/");

            int year = Integer.parseInt(splitDate[0]);
            int month = Integer.parseInt(splitDate[1]);

            if (month != 12) {
                month = month + 1;
            } else {
                year = year + 1;
                month = 1;
            }
            
            String saveMonth = null;
            
            if (month < 10) {
                saveMonth = "0" + month;
            }

            date = year + "/" + saveMonth + "/" + splitDate[2];

            String line = history.getType() + "|" + history.getAmount() + "|" + date +"|" + history.getCategory() + "|" + history.getPayment_method() + "|" + history.getAdd_type() + "|" + history.getNote();
            dummy += line;
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            fw = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(dummy);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveNextMonthly(String currentMonthly) {
        String path = null;

        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_monthly.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_monthly.data";
        }

        File file = new File(path);
        String dummy = "";
        FileWriter fw;
        String str;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            HistoryModel history = (HistoryModel) InterfaceManager.userHistory.get(InterfaceManager.userHistory.size() - 1);
            String date = history.getDate();
            String[] splitDate = date.split("/");

            int year = Integer.parseInt(splitDate[0]);
            int month = Integer.parseInt(splitDate[1]);

            if (month != 12) {
                month = month + 1;
            } else {
                year = year + 1;
                month = 1;
            }

            String saveMonth = null;

            if (month < 10) {
                saveMonth = "0" + month;
            }

            date = year + "/" + saveMonth + "/" + splitDate[2];

            String line = history.getType() + "|" + history.getAmount() + "|" + date + "|" + history.getCategory() + "|" + history.getPayment_method() + "|" + history.getAdd_type() + "|" + history.getNote();

            while ((str = reader.readLine()) != null) {
                if (str.equals(currentMonthly)) {
                    dummy += line + "\r\n";
                } else {
                    dummy += str + "\r\n";
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            fw = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(dummy);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveModifiedMonthly(String selectedHistory, String modifiedHistory) {
        String path = null;

        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_monthly.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_monthly.data";
        }

        File file = new File(path);
        String dummy = "";
        FileWriter fw;
        String str;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            while ((str = reader.readLine()) != null) {
                if (str.equals(selectedHistory)) {
                    dummy += modifiedHistory + "\r\n";
                } else {
                    dummy += str + "\r\n";
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            fw = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(dummy);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveModifiedHistory(String selectedHistory, String modifiedHistory) {
        String path = null;

        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_history.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_history.data";
        }

        File file = new File(path);
        String dummy = "";
        FileWriter fw;
        String str;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            while ((str = reader.readLine()) != null) {
                if (str.equals(selectedHistory)) {
                    dummy += modifiedHistory + "\r\n";
                } else {
                    dummy += str + "\r\n";
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            fw = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(dummy);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeHistory(JTable t, int row) {
        String path = null;
        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_history.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_history.data";
        }

        File file = new File(path);
        String dummy = "";
        FileWriter fw;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            String str;
            String history = "";

            for (int i = 0; i < 7; i++) {
                history = history + t.getModel().getValueAt(row, i).toString();
                if (i != 6) {
                    history = history + "|";
                }
            }

            while ((str = reader.readLine()) != null) {
                if (!str.equals(history)) {
                    dummy += (str + "\r\n");
                }
            }

            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            fw = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(dummy);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteMonthly(JTable t, int row) {
        String path = null;
        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_monthly.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_monthly.data";
        }

        File file = new File(path);
        String dummy = "";
        FileWriter fw;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            String str;
            String history = "";

            for (int i = 0; i < 7; i++) {
                history = history + t.getModel().getValueAt(row, i).toString();
                if (i != 6) {
                    history = history + "|";
                }
            }

            while ((str = reader.readLine()) != null) {
                if (!str.equals(history)) {
                    dummy += (str + "\r\n");
                }
            }

            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            fw = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(dummy);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveCategory(String a, String type) {
        String dummy = "";
        BufferedReader reader;
        FileWriter fw;

        String path = null;
        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_category.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_category.data";
        }

        if (Objects.equals(type, "income")) {
            try {
                reader = new BufferedReader(new FileReader(path));

                dummy += (reader.readLine() + a + "|" + "\r\n");
                dummy += (reader.readLine());

                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (Objects.equals(type, "expenditure")) {
            try {
                reader = new BufferedReader(new FileReader(path));

                dummy += (reader.readLine() + "\r\n");
                dummy += (reader.readLine() + a + "|");

                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            fw = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(dummy);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteCategory(String category, String type) {
        String dummy = "";
        BufferedReader reader;
        FileWriter fw;

        String path = null;
        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_category.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_category.data";
        }

        if (type == "income") {
            try {
                reader = new BufferedReader(new FileReader(path));

                String line = reader.readLine();
                String[] divCategory = line.split("\\|");

                for (int i = 0; i < divCategory.length; i++) {
                    if (!Objects.equals(divCategory[i], category)) {
                        dummy += divCategory[i] + "|";
                    }
                }

                dummy += ("\r\n" + reader.readLine());

                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (type == "expenditure") {
            try {
                reader = new BufferedReader(new FileReader(path));

                dummy += (reader.readLine() + "\r\n");

                String line = reader.readLine();
                String[] divCategory = line.split("\\|");

                for (int i = 0; i < divCategory.length; i++) {
                    if (!Objects.equals(divCategory[i], category)) {
                        dummy += divCategory[i] + "|";
                    }
                }
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            fw = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(dummy);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void changeCurrency(String currency) {
        String path = null;
        String dummy = "";
        BufferedReader reader;
        FileWriter fw;
        String str;

        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/user.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\user.data";
        }

        try {
            reader = new BufferedReader(new FileReader(path));

            while ((str = reader.readLine()) != null) {
                String[] divUser = str.split("\\|");

                if (Objects.equals(divUser[1], InterfaceManager.sessionUtil.getId())) {
                    dummy += InterfaceManager.sessionUtil.getUsername() + "|" + InterfaceManager.sessionUtil.getId() + "|" + InterfaceManager.sessionUtil.getPassword() + "|" + InterfaceManager.sessionUtil.getTotalIncome() + "|" + InterfaceManager.sessionUtil.getTotalExpenditure() + "|" + currency + "\r\n";
                } else {
                    dummy += str + "\r\n";
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            fw = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(dummy);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void changePassword(String newPassword) {
        String path = null;
        String dummy = "";
        BufferedReader reader;
        FileWriter fw;
        String str;

        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/user.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\user.data";
        }

        try {
            reader = new BufferedReader(new FileReader(path));

            while ((str = reader.readLine()) != null) {
                String[] divUser = str.split("\\|");

                if (Objects.equals(divUser[1], InterfaceManager.sessionUtil.getId())) {
                    dummy += InterfaceManager.sessionUtil.getUsername() + "|" + InterfaceManager.sessionUtil.getId() + "|" + newPassword + "|" + InterfaceManager.sessionUtil.getTotalIncome() + "|" + InterfaceManager.sessionUtil.getTotalExpenditure() + "|" + InterfaceManager.sessionUtil.getCurrency() + "\r\n";
                } else {
                    dummy += str + "\r\n";
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            fw = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(dummy);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        readUserFile();
    }

    public static void fileWriter(ArrayList<String> dataList, String filename) {
        FileOutputStream out;

        try {
            out = new FileOutputStream(filename);
            String data = "";
            for (int i = 0; i < dataList.size(); i++) {
                data += dataList.get(i) + "\n";
            }
            if (data.length() > 0) {
                data = data.substring(0, data.length() - 1);
            }
            byte[] totalBytes = data.getBytes();
            out.write(totalBytes);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readCategoryFile(String type) {
        InterfaceManager.number.clear();
        InterfaceManager.amount.clear();

        String path = null;
        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_category.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_category.data";
        }

        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(fr);
            String line = "";

            line = bf.readLine();
            String[] temp = line.split("\\|");

            if (type == "income") {
                for (int i = 1; i < temp.length; i++) {
                    InterfaceManager.number.put(temp[i], 0);
                    InterfaceManager.amount.put(temp[i], 0.0);
                }
            } else {
                line = bf.readLine();
                String[] temp2 = line.split("\\|");

                for (int i = 1; i < temp2.length; i++) {
                    InterfaceManager.number.put(temp2[i], 0);
                    InterfaceManager.amount.put(temp2[i], 0.0);
                }
            }

            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void readHistoryFile() {
        String path = null;
        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_history.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_history.data";
        }

        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(fr);
            String line = "";
            while ((line = bf.readLine()) != null) {
                String[] temp = line.split("\\|");
                String type = temp[0];
                double amount = Double.parseDouble(temp[1]);
                String date = temp[2];
                String category = (temp[3]);
                String payment_method = temp[4];
                String add_type = temp[5];
                String note = temp[6];
                HistoryModel history = new HistoryModel(type, amount, date, category, payment_method, add_type, note);
                InterfaceManager.userHistory.add(history);
            }
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void readUserFile() {
        String path = null;
        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/user.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\user.data";
        }

        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(fr);
            String line = "";
            while ((line = bf.readLine()) != null) {
                String[] temp = line.split("\\|");
                String username = temp[0];
                String id = temp[1];
                String password = temp[2];
                double totalExpenditure = Double.parseDouble(temp[3]);
                double totalIncome = Double.parseDouble(temp[4]);
                String age = temp[5];
                UserModel user = new UserModel(username, id, password, totalExpenditure, totalIncome, age);
                InterfaceManager.userList.add(user);
            }
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}