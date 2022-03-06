package com.toys.annotation_compiler;

import com.google.auto.service.AutoService;
import com.toys.annotation.BindPath;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * 目的 : 在这里生成 Java 代码
 */
@AutoService(value = Processor.class)
//@SupportedAnnotationTypes({"com.toys.annotation.BindPath"})
//@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
public class AnnotationCompiler extends AbstractProcessor {

    //使用 filter 对象来生成 Java 文件代码，filter 对象在 init() 方法中初始化，并可以直接从 processingEnv 得到
    Filer filer;
    Messager messager;

    /**
     * 初始化方法，执行顺序是在 process() 之前执行的
     *
     * @param processingEnvironment
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        //这个形参 processingEnvironment 和 processingEnv 是同一个对象，但是使用 processingEnv 的话不知道他是否为空，所以尽量使用 processingEnvironment 对象
        filer = processingEnvironment.getFiler();

        //Log 是运行时才能打印出来的，但是注解处理器是在编译时进行的，所以这里输出日志不能用 Log，用 System.out.println() 又比较 low
        messager = processingEnvironment.getMessager();
        messager.printMessage(Diagnostic.Kind.WARNING, "AnnotationCompiler init finish"); //Diagnostic : 诊断的
    }

    //步骤一: 声明当前注解处理器要处理的注解有哪些 (String 类型的参数传的就是注解的包名 + 类名，即全类名)

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new HashSet<>();
        annotations.add(BindPath.class.getCanonicalName()); //Canonical [kəˈnɒnɪkl] : 规范的
//      annotations.add(Override.class.getCanonicalName());
        return annotations;
    }

    //步骤二: 声明支持的 java 版本

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    /**
     * process() 方法的作用 : 去当前模块中搜索注解
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) { //roundEnvironment 表示 搜索引擎
        //目的: 去搜索当前模块用到了 BindPath 注解的类 (注解处理器的作用域只有当前模块)

        messager.printMessage(Diagnostic.Kind.WARNING, "AnnotationCompiler process pre");

        //Element ：类 (TypeElement)、成员变量 (VariableElement)、方法 (ExecutableElement)，就看注解 BindPath 放到了什么上面
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindPath.class);

        //遍历所有的类，然后把这个类的类名和注解里面携带的 key 拿出来
        Map<String, String> map = new HashMap<>(); // Map 的 key 就是注解里的 key，value 就是类名

        for (Element element : elements) {
            if (element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                //获取全类名
                String activityName = typeElement.getQualifiedName().toString();
                //获取上面的注解
                String key = typeElement.getAnnotation(BindPath.class).key();
                map.put(key, activityName + ".class");
            }
        }

        if (map.size() > 0) { //如果有被注解的 Activity 类，才需要生成 ActivityUtil
            generateActivityUtilFile(map);
        }

        messager.printMessage(Diagnostic.Kind.WARNING, "AnnotationCompiler process finish");

        return false;
    }

    private void generateActivityUtilFile(Map<String, String> annnKeyClassMap) {
        Writer writer = null;
        //生成文件
        try {
            String utilName = "ActivityUtil" + System.currentTimeMillis(); //这个 ActivityUtilxxx 的数量是和有多少依赖 annotation_compiler 的模块相关的，加上时间戳是为了防止类名重复
            JavaFileObject sourceFile = filer.createSourceFile("com.toys.utils." + utilName); //注意不要写成 filer.createClassFile()
            writer = sourceFile.openWriter();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("package com.toys.utils;\n");
            stringBuffer.append("import com.toys.arouter.IRouter;\n");
            stringBuffer.append("import com.toys.arouter.ARouter;\n");
            //如果 ActivityUtilxxx 要生成多个，则需要让它们实现 IRouter 接口，
            stringBuffer.append("public class " + utilName + " implements IRouter {\n");
            stringBuffer.append("@Override \n");
            stringBuffer.append("public void addActivity() {\n");
            Iterator<String> iterator = annnKeyClassMap.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String activityName = annnKeyClassMap.get(key);
                stringBuffer.append("ARouter.getInstance().addActivity(\"" + key + "\", " + activityName + ");");
            }
            stringBuffer.append("}\n}");
            writer.write(stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
            messager.printMessage(Diagnostic.Kind.WARNING, "AnnotationCompiler generateActivityUtilFile exception = " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
