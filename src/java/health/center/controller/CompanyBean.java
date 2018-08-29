package health.center.controller;

import health.center.service.CompanyServiceImpl;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author LEOGOLD
 */
@Named(value = "companyBean")
@RequestScoped
public class CompanyBean {

    private String email;
    private String phone;
    private String username;
    private String password;
    private String password2;
    private String title;
    private String companyName;
    private String fullName;
    private boolean signature;
    private String purposeOfPayment;
    private String paymentVoucherNum;
    private String amountInWords;
    private double amount;
    private String bank;
    private String receipt;
    private Date month;
    private Date dateOfPayment; 
    private String currentForm = "New Payment";
    private int currentStage = 1;
    private int pageCounter = 1;
    private String nextButton = "New Payment";
    private String previousButtton = "Cancel";
    private final int totalStage;
    private final String pageNavigation[] = {"index", "new_account", "new_payment", "confirmation"};
    private final Map<String, String> pageMap;

    @Autowired
    CompanyServiceImpl companyService;

    public CompanyBean() {
        this.pageMap = new HashMap<>();
        pageMap.clear();
        pageMap.put("index", "Cancel");
        pageMap.put("new_account", "New Account Creation");
        pageMap.put("new_payment", "New Payment");
        pageMap.put("confirmation", "Details Confirmation");
        totalStage = pageNavigation.length-1;
    }

    public String saveNewAccount() {

        pageCounter = 2;
        dynamicText(pageCounter);
        return "new_payment?faces-redirect=true";
    }

    public String confirmDetails() {

        pageCounter = 3;
        dynamicText(pageCounter);
        return "confirmation?faces-redirect=true";
    }

    public String addPayment() {

        pageCounter = 2;
        dynamicText(pageCounter);
        return "new_payment?faces-redirect=true";
    }

    public String login() {
        return "company_dashboard?faces-redirect=true";
    }

    public void dynamicText(int pageC) {
        currentStage = pageC;
        String cPage = null;
        while (pageC < pageNavigation.length) {
            cPage = pageNavigation[pageC];
            String mapValue = pageMap.get(cPage).toString();
            setNextButton(mapValue);
            setCurrentForm(mapValue);

        }

    }

    public String buttonPrevious() {
        int pageCounter = 1;
        if (pageCounter >= 1) {
            pageCounter--;
        }
        String cPage = pageNavigation[pageCounter];
        String mapValue = pageMap.get(cPage).toString();
        return cPage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isSignature() {
        return signature;
    }

    public void setSignature(boolean signature) {
        this.signature = signature;
    }

    public String getPurposeOfPayment() {
        return purposeOfPayment;
    }

    public void setPurposeOfPayment(String purposeOfPayment) {
        this.purposeOfPayment = purposeOfPayment;
    }

    public String getPaymentVoucherNum() {
        return paymentVoucherNum;
    }

    public void setPaymentVoucherNum(String paymentVoucherNum) {
        this.paymentVoucherNum = paymentVoucherNum;
    }

    public String getAmountInWords() {
        return amountInWords;
    }

    public void setAmountInWords(String amountInWords) {
        this.amountInWords = amountInWords;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
//
//    public String getAccountInfo() {
//        return accountInfo;
//    }
//
//    public void setAccountInfo(String accountInfo) {
//        this.accountInfo = accountInfo;
//    }
//
//    public String getPaymentInfo() {
//        return paymentInfo;
//    }
//
//    public void setPaymentInfo(String paymentInfo) {
//        this.paymentInfo = paymentInfo;
//    }
//
//    public String getConfirmInfo() {
//        return confirmInfo;
//    }
//
//    public void setConfirmInfo(String confirmInfo) {
//        this.confirmInfo = confirmInfo;
//    }

    public String getCurrentForm() {
        return currentForm;
    }

    public void setCurrentForm(String currentForm) {
        this.currentForm = currentForm;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public CompanyServiceImpl getCompanyService() {
        return companyService;
    }

    public void setCompanyService(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }

    public String getNextButton() {
        return nextButton;
    }

    public void setNextButton(String nextButton) {
        this.nextButton = nextButton;
    }

    public String getPreviousButtton() {
        return previousButtton;
    }

    public void setPreviousButtton(String previousButtton) {
        this.previousButtton = previousButtton;
    }

//    public int getPageCounter() {
//        return pageCounter;
//    }
//
//    public void setPageCounter(int pageCounter) {
//        this.pageCounter = pageCounter;
//    }
    public int getTotalStage() {
        return totalStage;
    }

}
