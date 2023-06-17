package banckend;


import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.util.SerialParameters;
import io.github.cdimascio.dotenv.Dotenv;

public class SerialModbusExample {
    Dotenv dotenv;

    public SerialModbusExample(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    public static void main(String[] args) {

        SerialModbusExample example = new SerialModbusExample(Dotenv.load());
        example.modbusSerialMaster();

    }

    private SerialParameters serialParameters() {
        SerialParameters params = new SerialParameters();
        params.setPortName(dotenv.get("PORT_NAME"));
        params.setBaudRate(4800);
        params.setDatabits(8);
        params.setParity("none");
        params.setStopbits(1);
        return params;
    }

    private void modbusSerialMaster() {


        String[] slaveIDS = dotenv.get("SLAVE_IDS").split(",");
        ModbusSerialMaster modbusMaster = new ModbusSerialMaster(serialParameters());

        for (String slave : slaveIDS) {
            try {

                modbusMaster.connect();

                int slaveId = Integer.parseInt(slave);
                int startAddress = 0;
                int numInputs = 10;
                for (int ix = 0; ix < 50; ix++) {

                    Register[] datos = modbusMaster.readMultipleRegisters(slaveId, startAddress, numInputs);
                    Thread.sleep(5000);

                    for (Register data : datos) {
                        System.out.print(" " + data.toString());
                    }
                    System.out.println();
                }

                modbusMaster.disconnect(); // Desconectar del puerto serial
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }
}