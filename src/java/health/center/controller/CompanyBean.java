package health.center.controller;

import health.center.model.Company;
import health.center.service.CompanyService;
import health.center.model.Payment;
import health.center.utils.FileUpload;
import health.center.utils.SessionUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author LEOGOLD
 */
@Named(value = "companyBean")
@SessionScoped
public class CompanyBean implements java.io.Serializable {

    private String email;
    private String phone;
    private String username;
    private String password;
    private String password2;
    private String title;
    private String userType;
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
    private Date createdDate;
    private Date modifiedDate;
    private Date dateOfPayment;
    private String currentForm;
    private int currentStage = 1;
    private int pageCounter = 1;
//    private String nextButton = "New Payment";
//    private String previousButtton = "Cancel";
    private final int totalStage;
    private String loginBtn = "Login";
    private UploadedFile paymentReceipt;
    private final String pageNavigation[] = {"index", "new_account", "new_payment", "confirmation"};
    private final Map<String, String> pageMap;
    private Payment payment;
    private Company company;
    @Autowired
    CompanyService companyService;

    public CompanyBean() {
        this.pageMap = new HashMap<>();
        pageMap.clear();
        pageMap.put("index", "Cancel");
        pageMap.put("new_account", "New Account Creation");
        pageMap.put("new_payment", "New Payment");
        pageMap.put("confirmation", "Details Confirmation");
        totalStage = pageNavigation.length - 1;
    }

    public String saveNewAccount() {
        Company company = new Company(username, password, companyName, fullName, phone, email);
        companyService.save(company);
        clearCompanyDetails();
        FacesMessage message = new FacesMessage("Registration successful! Please login to make payment");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, message);
        return "login";
    }

    public void setCompanyDetails(Company company) {
        setUsername(company.getUsername());
        setCompanyName(company.getCompanyName());
        setPhone(company.getPhoneNumber());
        setEmail(company.getEmail());
    }

    public void clearCompanyDetails() {
        setUsername(null);
        setCompanyName(null);
        setPhone(null);
        setEmail(null);

    }

    public void handleFileUpload(FileUploadEvent event) {
        receipt = new FileUpload().upload(event.getFile(), username);
        if (!receipt.equals("error")) {
            FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage("Receipt upload FAILED", "Ensure that file is either a pdf or image file, and file size is less than 2MB");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public String proceedToConfirmation() {
        if (receipt != null && !receipt.equals("error")) {
            Company company = new Company();
            company.setCompanyId(SessionUtils.getCompanyId());
            Payment companyPayment = new Payment(company, fullName, title, signature, purposeOfPayment, paymentVoucherNum, amountInWords, amount, bank, receipt, month, dateOfPayment);
            setPayment(companyPayment);
            return "confirmation?faces-redirect=true";
        } else {
            FacesMessage message = new FacesMessage("Error", "No receipt has been uploaded");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "new_payment";
        }
    }

    public String makePayment() {
        Payment companyPayment = this.payment;
        companyService.makePayment(companyPayment);
        clearPaymentFields();
        setPayment(null);
        pageCounter = 3;
        dynamicText(pageCounter);
        return "company_dashboard?faces-redirect=true";
    }

    public String newPayment() {
        clearPaymentFields();

        HttpSession userSession = SessionUtils.getSession();
        if (userSession.getAttribute("username") != null) {
            System.out.println("this is the user session " + userSession);
            pageCounter = 2;
            dynamicText(pageCounter);

            return "new_payment?faces-redirect=true";
        } else {
            pageCounter = 1;
            dynamicText(pageCounter);
            return "new_account?faces-redirect=true";
        }
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void clearPaymentFields() {
        setFullName(null);
        setTitle(null);
        setPurposeOfPayment(null);
        setPaymentVoucherNum(null);
        setAmountInWords(null);
        setAmount(0);
        setBank(null);
        setReceipt(null);
        setMonth(null);
    }

    public String paymentDetails(Payment payment) {
        setAmount(payment.getAmount());
        setAmountInWords(payment.getAmountInWords());
        setBank(payment.getBank());
        setFullName(payment.getFullName());
        setPaymentVoucherNum(payment.getPaymentVoucherNum());
        setPurposeOfPayment(payment.getPurposeOfPayment());
        setMonth(payment.getMonth());
        setCreatedDate(payment.getCreated());
        setModifiedDate(payment.getModified());
        setTitle(payment.getTitle());

        return "payment_details?faces-redirect=true";
    }

    public String oneCompanyReceipt(int companyId) {
//        The list of all the receipts from the db for a particular company should asigned to allReceipt variabe
        return "all_payment?faces-redirect=true";
    }

    public String receiptToPdf(int paymentId) {
        //add your code here 
        return "html_to_pdf?faces-redirect=true";
    }

    public void printReceipt() {

    }

    public String updateDetails() {
        return "confrimation?faces-redirect=true";
    }

    public String loginControlBtn() {
        HttpSession session = SessionUtils.getSession();
        if (session.getAttribute("username") != null) {
            session.invalidate();
            setLoginBtn("Login");
            FacesMessage message = new FacesMessage("Log Out", "Log out successfully");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "index?faces-redirect=true";
        } else {
            return "login?faces-redirect=true";
        }
    }

    public void loginBtn() {
        switch(userType){
            case "U":login();
            break;
            case "A": new AdminController().login();
            break;
            default : login();
        }
    }

    public String login() {
        try {
            Company company = companyService.login(username, password);
            SessionUtils.getSession().setAttribute("companyId", company.getCompanyId());
            SessionUtils.getSession().setAttribute("username", company.getUsername());
            setCompanyDetails(company);
            setCompany(company);
            setLoginBtn("Log Out");
            return "company_dashboard?faces-redirect=true";
        } catch (NullPointerException e) {
            return "login?faces-redirect=true";
        }
    }

    public String logout() {
        SessionUtils.getSession().invalidate();
        this.clearCompanyDetails();
        return "index?faces-redirect=true";
    }

    public List<Payment> getAllPayments() {
        return companyService.getAllPayments(SessionUtils.getCompanyId());
    }

    public List<Company> getAllCompanies() {
        return companyService.retrieveAll();
    }

    public List<Payment> allPayment() {
        return null;
    }

    public List<Company> allCompany() {
        return null;
    }

    public void dynamicText(int pageC) {
        currentStage = pageC;
        String cPage = null;
        cPage = pageNavigation[pageC];
        String mapValue = pageMap.get(cPage);
        setCurrentForm(mapValue);
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public UploadedFile getPaymentReceipt() {
        return paymentReceipt;
    }

    public void setPaymentReceipt(UploadedFile paymentReceipt) {
        this.paymentReceipt = paymentReceipt;
    }

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

    public CompanyService getCompanyService() {
        return companyService;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

//    public String getNextButton() {
//        return nextButton;
//    }
//
//    public void setNextButton(String nextButton) {
//        this.nextButton = nextButton;
//    }
//
//    public String getPreviousButtton() {
//        return previousButtton;
//    }
//
//    public void setPreviousButtton(String previousButtton) {
//        this.previousButtton = previousButtton;
//    }
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getLoginBtn() {
        return loginBtn;
    }

    public void setLoginBtn(String loginBtn) {
        this.loginBtn = loginBtn;
    }

    /**
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(Company company) {
        this.company = company;
    }

}
