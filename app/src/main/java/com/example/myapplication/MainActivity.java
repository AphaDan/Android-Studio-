package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "CalcActivity";
    private TextView tv_result;//声明一个文本视图对象
    private String operator = "";//运算符
    private String firstNum = "";//第一个操作数
    private String secondNum = "";//第二个操作数
    private String result = "";//当前的计算结果
    private String showText = "";//显示的文本内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        //从布局文件中获取名叫tv_resut的文本视图
        tv_result = findViewById(R.id.tv_result);
        //下面给每个按钮控件都注册了点击监听器
        findViewById(R.id.btn_plusorminus).setOnClickListener(this);//“正负转换”按钮
        findViewById(R.id.btn_sin).setOnClickListener(this);//“sin”按钮
        findViewById(R.id.btn_cos).setOnClickListener(this);//“cos”按钮
        findViewById(R.id.btn_power).setOnClickListener(this);//“幂”按钮
        findViewById(R.id.btn_percent).setOnClickListener(this);//“百分号”按钮
        findViewById(R.id.btn_divide).setOnClickListener(this); // “除法”按钮
        findViewById(R.id.btn_multiply).setOnClickListener(this); // “乘法”按钮
        findViewById(R.id.btn_clear).setOnClickListener(this); // “清除”按钮
        findViewById(R.id.btn_seven).setOnClickListener(this); // 数字7
        findViewById(R.id.btn_eight).setOnClickListener(this); // 数字8
        findViewById(R.id.btn_nine).setOnClickListener(this); // 数字9
        findViewById(R.id.btn_plus).setOnClickListener(this); // “加法”按钮
        findViewById(R.id.btn_four).setOnClickListener(this); // 数字4
        findViewById(R.id.btn_five).setOnClickListener(this); // 数字5
        findViewById(R.id.btn_six).setOnClickListener(this); // 数字6
        findViewById(R.id.btn_minus).setOnClickListener(this); // “减法”按钮
        findViewById(R.id.btn_one).setOnClickListener(this); // 数字1
        findViewById(R.id.btn_two).setOnClickListener(this); // 数字2
        findViewById(R.id.btn_three).setOnClickListener(this); // 数字3
        findViewById(R.id.btn_reciprocal).setOnClickListener(this); // 求倒数按钮
        findViewById(R.id.btn_zero).setOnClickListener(this); // 数字0
        findViewById(R.id.btn_dot).setOnClickListener(this); // “小数点”按钮
        findViewById(R.id.btn_equal).setOnClickListener(this); // “等号”按钮
        findViewById(R.id.ib_sqrt).setOnClickListener(this); // “开平方”按钮
    }

    private boolean verify(View v) {
        if (v.getId() == R.id.btn_equal) { // 点击了等号按钮
            if (operator.equals("")) { // 无运算符
                Toast.makeText(this, "请输入运算符", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (firstNum.equals("") || secondNum.equals("")) { // 无操作数
                Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (operator.equals("÷") && Double.parseDouble(secondNum) == 0) { // 除数为零
                Toast.makeText(this, "零不能做除数！", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (v.getId() == R.id.btn_plus || v.getId() == R.id.btn_minus // 点击了加、减、乘、除
                || v.getId() == R.id.btn_multiply || v.getId() == R.id.btn_divide||v.getId() == R.id.btn_power) {
            if (firstNum.equals("")) { // 缺少第一个操作数
                Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!operator.equals("")) { // 已有运算符
                Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else if (v.getId() == R.id.ib_sqrt) { // 点击了开根号按钮
            if (firstNum.equals("")) { // 缺少底数
                Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
                return false;
            }
            else if (Double.parseDouble(firstNum) < 0) { // 不能对负数开平方
                Toast.makeText(this, "不能对负数开平方！", Toast.LENGTH_SHORT).show();
                return false;
            }

        }
        else if(v.getId() == R.id.btn_cos||v.getId() == R.id.btn_sin||v.getId() == R.id.btn_plusorminus||v.getId() == R.id.btn_percent){
            if (firstNum.equals("")) { // 缺少底数
                Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (v.getId() == R.id.btn_reciprocal) { // 点击了求倒数按钮
            if (firstNum.equals("")) { // 缺少底数
                Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (Double.parseDouble(firstNum) == 0) { // 不能对零求倒数
                Toast.makeText(this, "不能对零求倒数！", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (v.getId() == R.id.btn_dot) { // 点击了小数点
            if ((operator.equals("") && firstNum.contains("."))| (!operator.equals("") && secondNum.contains(".")) ) { // 检查是否已有小数点
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (!verify(v)) { // 未通过合法性校验，直接返回
            return;
        }
        String inputText;
        if (v.getId() == R.id.ib_sqrt) { // 如果是开根号按钮
            inputText = "√";
        } else if(v.getId() == R.id.btn_plusorminus) {// 如果是转换正负按钮
            inputText = "";
        }else { // 除了开根号之外的其他按钮
            inputText = ((TextView) v).getText().toString();
        }
        Log.d(TAG, "inputText=" + inputText);
        if (v.getId() == R.id.btn_clear) { // 点击了清除按钮
            clear();
        } else if(v.getId() == R.id.btn_plusorminus){
            double calculate_result = 0-Double.parseDouble(firstNum); // 转换正负运算
            refreshOperate(String.valueOf(calculate_result));
            refreshText(result);
        }else if (v.getId() == R.id.btn_percent) { // 点击了百分号按钮
            double calculate_result = calculatePercent(Double.parseDouble(firstNum)); // 百分号运算
            refreshOperate(String.valueOf(calculate_result));
            refreshText(showText + "%=" + result);
        }else if (v.getId() == R.id.btn_sin) { // 点击了sin按钮
            double calculate_result = Math.sin(Double.parseDouble(firstNum)); // sin运算
            refreshOperate(String.valueOf(calculate_result));
            refreshText("sin"+showText + "=" + result);
        } else if (v.getId() == R.id.btn_cos) { // 点击了cos按钮
            double calculate_result = Math.cos(Double.parseDouble(firstNum)); // 百分号运算
            refreshOperate(String.valueOf(calculate_result));
            refreshText("cos"+showText + "=" + result);
        }  else if (v.getId() == R.id.btn_plus || v.getId() == R.id.btn_minus // 点击了加、减、乘、除、求幂按钮
                || v.getId() == R.id.btn_multiply || v.getId() == R.id.btn_divide||v.getId() == R.id.btn_power) {
            operator = inputText; // 运算符
            refreshText(showText + operator);
        } else if (v.getId() == R.id.btn_equal) { // 点击了等号按钮
            double calculate_result = calculateFour(); // 加减乘除四则运算
            refreshOperate(String.valueOf(calculate_result));
            refreshText(showText + "=" + result);
        } else if (v.getId() == R.id.ib_sqrt) { // 点击了开根号按钮
            double calculate_result = Math.sqrt(Double.parseDouble(firstNum)); // 开平方运算
            refreshOperate(String.valueOf(calculate_result));
            refreshText("√" +showText +"="+ result);
        } else if (v.getId() == R.id.btn_reciprocal) { // 点击了求倒数按钮
            double calculate_result = 1.0 / Double.parseDouble(firstNum); // 求倒数运算
            refreshOperate(String.valueOf(calculate_result));
            refreshText(showText + "/=" + result);
        } else { // 点击了其他按钮，包括数字和小数点
            if (result.length() > 0 && operator.equals("")) { // 上次的运算结果已经出来了
                clear();
            }
            if (operator.equals("")) { // 无运算符，则继续拼接第一个操作数
                firstNum = firstNum+inputText;
            } else { // 有运算符，则继续拼接第二个操作数
                secondNum = secondNum + inputText;
            }
            if (showText.equals("0") && !inputText.equals(".")) { // 整数不需要前面的0
                refreshText(inputText);
            } else {
                refreshText(showText + inputText);
            }
        }
    }

    // 刷新运算结果
    private void refreshOperate(String new_result) {
        result = new_result;
        firstNum = result;
        secondNum = "";
        operator = "";
    }

    // 刷新文本显示
    private void refreshText(String text) {
        showText = text;
        tv_result.setText(showText);
    }

    // 清空并初始化
    private void clear() {
        refreshOperate("");
        refreshText("");
    }

    // 加减乘除四则运算，返回计算结果
    private double calculateFour() {
        double calculate_result = 0;
        if (operator.equals("＋")) { // 当前是相加运算
            calculate_result = Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
        } else if (operator.equals("－")) { // 当前是相减运算
            calculate_result = Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
        } else if (operator.equals("×")) { // 当前是相乘运算
            calculate_result = Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
        } else if (operator.equals("÷")) { // 当前是相除运算
            calculate_result = Double.parseDouble(firstNum) / Double.parseDouble(secondNum);
        }else if (operator.equals("^")) { // 当前是相除运算
            calculate_result = Math.pow(Double.parseDouble(firstNum) , Double.parseDouble(secondNum));
        }
        Log.d(TAG, "calculate_result=" + calculate_result); // 把运算结果打印到日志中
        return calculate_result;
    }
    private double calculatePercent(double calculate_result) {
        calculate_result = Double.parseDouble(firstNum) /100; // 百分比计算符
        Log.d(TAG, "calculate_result=" + calculate_result); // 把运算结果打印到日志中
        return calculate_result;
    }
}
