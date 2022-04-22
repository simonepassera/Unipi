from learning import *
from utils import *

def demo_ll_regression1():
    # modello lineare applicato al task di regressione 'simplefit'
    d = DataSet(name="simplefit")
    n_folds = 5 # 5-fold cross validation
    cv_performance = cross_validation(LinearLearner, d, loss = mse_loss, k = n_folds,
                                      learning_rate = 0.01, epochs = 1000)
    
    
    print('Performance del modello lineare sul task di regressione "simplefit"')
    print('TR MSE = %s -- VL MSE = %s' % (cv_performance[0],cv_performance[1]))


def demo_ll_regression2():
    # modello lineare applicato al task di regressione 'abalone'
    d = DataSet(name="abalone")
    n_folds = 5 # 5-fold cross validation
    cv_performance = cross_validation(LinearLearner, d, loss = mse_loss, k = n_folds, learning_rate = 0.01, epochs = 100)
    
    
    print('Performance del modello lineare sul task di regressione "abalone"')
    print('TR MSE = %s -- VL MSE = %s' % (cv_performance[0],cv_performance[1]))


def demo_ll_regression3():
    # modello lineare applicato al task di regressione 'bodyfat'
    d = DataSet(name="bodyfat")
    n_folds = 5 # 5-fold cross validation
    cv_performance = cross_validation(LinearLearner, d, loss = mse_loss, k = n_folds, learning_rate = 0.00001, epochs = 10000)
    
    
    print('Performance del modello lineare sul task di regressione "bodyfat"')
    print('TR MSE = %s -- VL MSE = %s' % (cv_performance[0],cv_performance[1]))

def demo_ll_iris():
    # modello lineare applicato al task di classificazione 'iris'
    d = DataSet(name="iris")

    # il codice in LinearLearner e' adatto solo al caso di classificazione binaria
    # quindi il dataset originale viene ridotto eliminando gli esempi di 'virginica'
    # per ottenere un problema di classificazione binaria
    d.remove_examples("virginica")
    # trasformiamo anche il target in formato numerico:
    classes = ["setosa","versicolor"]
    d.classes_to_numbers(classes)

    n_folds = 5 # 5-fold cross validation
    cv_performance = cross_validation(LinearLearner, d, loss = accuracy_binary, k = n_folds, learning_rate = 0.01, epochs = 100)
    print('Performance del modello lineare sul task di classificazione "iris" (in versione binaria)')
    print('TR accuracy = %s -- VL accuracy = %s' % (cv_performance[0],cv_performance[1]))

def demo_ll_regression_weights():
    # modello lineare applicato al task di regressione 'simplefit'
    d = DataSet(name="simplefit")
    n_folds = 5 # 5-fold cross validation
    ho_data = hold_out(LinearLearner, d, loss = mse_loss, propTr = 0.70, propVl = 0.20, learning_rate = 0.01, epochs = 1000)
    
    print('Performance del modello lineare sul task di regressione "simplefit"')
    print('TR MSE = %s -- VL MSE = %s -- TS MSE = %s' % (ho_data[0],ho_data[1],ho_data[2]))
    print('I parametri del modello lineare appresi sul training set: %s ' % ho_data[3])
    print('MSE sul training set al variare delle epoche: %s' % ho_data[4])


# MAIN HERE:
demo_ll_regression1()

    

