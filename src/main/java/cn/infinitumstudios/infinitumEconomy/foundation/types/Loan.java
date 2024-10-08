package cn.infinitumstudios.infinitumEconomy.foundation.types;

import cn.infinitumstudios.infinitumEconomy.foundation.Currency;
import cn.infinitumstudios.infinitumEconomy.foundation.database.CurrencyDatabase;
import cn.infinitumstudios.infinitumEconomy.foundation.interfaces.IJsonConvertible;
import com.google.gson.JsonObject;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class Loan implements IJsonConvertible<Loan> {
    private UUID loanUUID, lenderUUID, borrowerUUID, loanBankUUID, usedCurrencyUUID;
    private double value, interestRate;

    // Loan from player account
    public Loan(OfflinePlayer borrower, OfflinePlayer loaner, double value, double interestRate, UUID currency){
        this.lenderUUID = borrower.getUniqueId();
        this.borrowerUUID = loaner.getUniqueId();
        this.usedCurrencyUUID = currency;
        this.value = value;
        this.interestRate = interestRate;
        this.loanUUID = UUID.randomUUID();
        this.loanBankUUID = null;
    }

    // Loan from bank account
    public Loan(OfflinePlayer borrower, Bank loanBank, double value, double interestRate, UUID currency){
        this.lenderUUID = borrower.getUniqueId();
        this.borrowerUUID = null;
        this.usedCurrencyUUID = currency;
        this.value = value;
        this.interestRate = interestRate;
        this.loanUUID = UUID.randomUUID();
        this.loanBankUUID = loanBank.getBankUUID();
    }

    public UUID getLoanUUID(){
        return loanUUID;
    }

    public UUID getLenderUUID(){
        return lenderUUID;
    }

    public UUID getBorrowerUUID(){
        return borrowerUUID;
    }

    public UUID getLoanBankUUID(){
        return loanBankUUID;
    }

    public UUID getUsedCurrencyUUID(){
        return usedCurrencyUUID;
    }

    public void setInterestRate(double interestRate){
        this.interestRate = interestRate;
    }

    public double getInterestRate(){
        return interestRate;
    }

    public void setValue(double value){
        this.value = value;
    }

    public double getValue(){
        return value;
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uuid", loanUUID.toString());
        jsonObject.addProperty("lender", lenderUUID.toString());
        jsonObject.addProperty("borrower", borrowerUUID.toString());
        jsonObject.addProperty("bank", loanBankUUID.toString());
        jsonObject.addProperty("currency", usedCurrencyUUID.toString());
        jsonObject.addProperty("value", value);
        jsonObject.addProperty("interestRate", interestRate);
        return jsonObject;
    }

    @Override
    public void fromJson(JsonObject object) {
        this.loanUUID = UUID.fromString(object.get("uuid").getAsString());
        this.lenderUUID = UUID.fromString(object.get("lender").getAsString());
        this.borrowerUUID = UUID.fromString(object.get("borrower").getAsString());
        this.loanBankUUID = UUID.fromString(object.get("bank").getAsString());
        this.usedCurrencyUUID = UUID.fromString(object.get("currency").getAsString());
        this.value = object.get("value").getAsDouble();
        this.interestRate = object.get("interestRate").getAsDouble();
    }
}
