package quiles.com.agendaandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

/**
 * Created by Jefferson on 19/01/2017.
 */

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // caixa para receber as mensagens de outra tela
        Bundle bundle = intent.getExtras();
        Object[] mensagens = (Object[]) bundle.get("pdus");

       //pegará o primeiro item das mensagens
        byte [] mensagem = (byte[]) mensagens[0];
        SmsMessage msg = SmsMessage.createFromPdu(mensagem);
        // string para receber o número de contato de quem enviou a mensagem
        String telefone = msg.getDisplayOriginatingAddress();

        ContatoDAO dao = new ContatoDAO(context);

        if(dao.isContato(telefone)){
            MediaPlayer player = MediaPlayer.create(context, R.raw.music);
            player.start();
        }

    }
}
