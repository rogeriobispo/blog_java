package blog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@EnableWebMvc // habilita a aplica��o Web
@Configuration // habilita para reconhecer a classe de configura��o
@ComponentScan(basePackages="blog") //local onde o Spring buscar� os controllers 

public class AppConfig extends WebMvcConfigurerAdapter{
	
	//resolve as requisi��es das views
	@Override
	public void  configureViewResolvers(ViewResolverRegistry registry){
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
		viewResolver.setPrefix("/WEB-INF/");	//local onde se encontram as views
		viewResolver.setSuffix(".jsp");			//tipo de arquivo das views (.jsp)
		viewResolver.setViewClass(JstlView.class);
		registry.viewResolver(viewResolver);
	}	
	
}
