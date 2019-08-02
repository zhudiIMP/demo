package com.example.interceptor;

import com.example.dto.commndto.response.Head;
import com.example.dto.commndto.response.ResponseVo;
import com.haiercash.cmis.product.interceptor.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * @author
 * @date
 */
@ControllerAdvice
public class GlobalExceptionHandler {


	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResponseVo<?> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		Head head=new Head();
		String errorMessage=getStackTrace(e);
		if (e instanceof org.springframework.web.bind.MethodArgumentNotValidException) {
			MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;
			List<ObjectError> errors = validException.getBindingResult().getAllErrors();
			StringBuffer errorMsg = new StringBuffer();
			errors.stream().forEach(error -> errorMsg.append(error.getDefaultMessage()).append(";"));
			head.setRetMsg(errorMsg.toString());
			head.setRetFlag("60000001");
			logger.info(errorMessage);
			binaryReader(req, logger);
			return ResponseVo.failure(head,null, logger);
		}else if(e instanceof org.springframework.http.converter.HttpMessageNotReadableException){
			head.setRetFlag("60000001");
			logger.info(errorMessage);
			binaryReader(req, logger);
			return ResponseVo.failure(head,null, logger);
		}else{
			head.setRetFlag("99999999");
			head.setRetMsg("其他未知异常:"+ e.getMessage());
			binaryReader(req, logger);
			logger.info(errorMessage);
			return ResponseVo.failure(head,null, logger);
		}


//		if (e instanceof java.lang.NullPointerException) {
//			head.setErrorCode("0000010");
//			head.setErrorMessage("空指针异常，请输入正确的参数");
//			return ResponseVo.failure(head,null);
//		}

	}

	//获取请求报文
	private  void binaryReader(HttpServletRequest request, Logger logger)  {

		try{
			BufferedReader br = request.getReader();
			String str;
			StringBuffer wholeStr = new StringBuffer();
			while((str = br.readLine()) != null){
				wholeStr.append(str) ;
			}
			logger.error("请求报文：" + wholeStr);
		}catch(Exception ex){
			//logger.warn("输出请求报文时出错：" + ex.toString());
		}


	}

	@ExceptionHandler(value = BizException.class)
	@ResponseBody
	public ResponseVo<?> bizErrorHandler(HttpServletRequest req, Exception e) {
		BizException ex = (BizException) e;
		Head head=new Head();
		head.setRetFlag(ex.getErrorMessage());
		head.setRetMsg(ex.getErrorCode());
		return ResponseVo.failure(head,null);
	}



    private String getForwardHost(HttpServletRequest req) {
        if (req.getHeader("X-Forwarded-For") == null) {
            return req.getRemoteAddr();
        }
        return req.getHeader("X-Forwarded-For");
    }

    private String getRealIP(HttpServletRequest req) {
        if (req.getHeader("X-Real-IP") == null) {
            return req.getRemoteAddr();
        }
        return req.getHeader("X-Real-IP");
    }

    private  String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        String stackTraceString = sw.getBuffer().toString();
        pw.close();
        try {
            sw.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return stackTraceString;
    }
}
