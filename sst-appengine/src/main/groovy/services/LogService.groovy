package services

import groovy.transform.CompileStatic
import groovyx.gaelyk.GaelykBindingEnhancer
import groovyx.gaelyk.GaelykBindings

import java.util.logging.Logger

//@GaelykBindings
//@CompileStatic
class LogService {

    Logger log = Logger.getLogger(this.class.name)

    def binding

    public LogService(binding) {
        this.binding = binding
        binding.log.info "binding.log.info "
        log.info "log.info"
    }

    void test() {
        binding.log.info("test() - binding.log.info - binding.class: "+binding.class)
        log.info("test() - log.info - binding.class: "+binding.class)
       // binding.datastore.hi {}
    }
}
