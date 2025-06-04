package sg.nus.iss.service.ecommerceapp.service.impl;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import sg.nus.iss.service.ecommerceapp.service.OtpService;

//Author(s): Ramukannu Suguna

@Service
public class SmsOtpServiceImpl implements OtpService {

    private static final String ACCOUNT_SID = "AC3e##################";
    
    private static final String AUTH_TOKEN = "0c####################";
    private static final String FROM_PHONE = "+185########";
    
//    private SmsOtpServiceImpl smsOtpServiceImpl;

    public SmsOtpServiceImpl() {
    	Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }


    @Override
    public void sendOtp(String mobileNumber, String otp) {
    	System.out.println("mobileNumber"+ mobileNumber);
        Message.creator(
            new PhoneNumber("+65"+mobileNumber),
            new PhoneNumber(FROM_PHONE),
            "Your verification code is: " + otp + ". Do not share it with anyone"
        ).create();
    }
}
