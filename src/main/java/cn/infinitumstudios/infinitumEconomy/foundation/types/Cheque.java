package cn.infinitumstudios.infinitumEconomy.foundation.types;

import cn.infinitumstudios.infinitumEconomy.foundation.Currency;
import cn.infinitumstudios.infinitumEconomy.foundation.database.CurrencyDatabase;
import cn.infinitumstudios.infinitumEconomy.foundation.interfaces.IJsonConvertible;
import com.google.gson.JsonObject;
import org.bukkit.OfflinePlayer;

import javax.annotation.Nullable;
import java.util.UUID;

public class Cheque implements IJsonConvertible<Cheque> {
    private double chequeWorth;
    private String chequeOwner;
    private UUID chequeOwnerUUID;
    private UUID chequeUUID;
    private UUID chequeCurrencyUUID;

    public Cheque(OfflinePlayer owner, double chequeWorth, UUID chequeCurrencyUUID){
        this.chequeWorth = chequeWorth;
        this.chequeOwner = owner.getName();
        this.chequeOwnerUUID = owner.getUniqueId();
        this.chequeUUID = UUID.randomUUID();
        this.chequeCurrencyUUID = chequeCurrencyUUID;
    }

    public double getWorth() {
        // Converted into Universal currency
        return chequeWorth;
    }

    public String getOwnerName() {
        return chequeOwner;
    }

    public UUID getUUID() {
        return chequeUUID;
    }

    public UUID getOwnerUUID() {
        return chequeOwnerUUID;
    }

    public UUID getCurrencyUUID() {
        return chequeCurrencyUUID;
    }

    public @Nullable Currency getCurrency(){
        CurrencyDatabase db = new CurrencyDatabase();
        db.load();
        return db.read(currency -> currency.getCurrencyID().equals(chequeCurrencyUUID)).get();
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("chequeWorth", chequeWorth);
        jsonObject.addProperty("chequeOwner", chequeOwner);
        jsonObject.addProperty("chequeOwnerUUID", chequeOwnerUUID.toString());
        jsonObject.addProperty("chequeUUID", chequeUUID.toString());
        jsonObject.addProperty("chequeCurrency", chequeCurrencyUUID.toString());
        return jsonObject;
    }

    @Override
    public void fromJson(JsonObject object) {
        this.chequeWorth = object.get("chequeWorth").getAsDouble();
        this.chequeOwner = object.get("chequeOwner").getAsString();
        this.chequeOwnerUUID = UUID.fromString(object.get("chequeOwnerUUID").getAsString());
        this.chequeUUID = UUID.fromString(object.get("chequeUUID").getAsString());
        this.chequeCurrencyUUID = UUID.fromString(object.get("chequeCurrency").getAsString());
    }
}
