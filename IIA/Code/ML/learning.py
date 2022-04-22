from utils import *

import heapq
import math
from statistics import mean, stdev



# ______________________________________________________________________________
# Further useful functions for learning.py

def euclidean_distance(X, Y):
    return math.sqrt(sum([(x - y)**2 for x, y in zip(X, Y)]))


def rms_error(X, Y):
    return math.sqrt(ms_error(X, Y))


def ms_error(X, Y):
    return mean([(x - y)**2 for x, y in zip(X, Y)])


def mean_error(X, Y):
    return mean([abs(x - y) for x, y in zip(X, Y)])


def manhattan_distance(X, Y):
    return sum([abs(x - y) for x, y in zip(X, Y)])


def mean_boolean_error(X, Y):
    return mean(int(x != y) for x, y in zip(X, Y))


def hamming_distance(X, Y):
    return sum(x != y for x, y in zip(X, Y))

# ______________________________________________________________________________




class DataSet:
    """ Questa classe implementa l'astrazione di un dataset per un ML task
        Ha i seguenti campi:
        
    d.examples   Una lista di esempi. Ogni esempio e' una lista di valori di attributi.
    d.attrs      Una lista di indici interi all'interno delle posizioni di ciascun esempio.
                 Ogni posizione indicata in d.attrs si riferisce ad un attributo all'interno di ciascun esempio.
    d.attrnames  Lista opzionale di nomi mnemonici per gli attributi corrispondenti.
    d.target     L'indice corrispondente alla variabile che rappresenta il target in ciascun esempio.
                 Per default e' uguale all'attributo finale.
    d.inputs     Lista contenente gli indici corrispondenti alla variabili di input in ciascun esempio.
    d.values     Una lista di liste: ogni sottolista rappresenta l'insieme di possibili valori per l'atributo corrispondente.
                 Se inizialmente None, viene calcolato dagli esempi noti da self.setproblem.
                 Se diverso da None, un valore errato genera un ValueError.
    d.distance   Una funzione (distanza) da una coppia di esempi ad un valore non-negativo.
    d.name       Nome del dataset (solo a fini di visualizzazione).
    d.source     Sorgente del dataset (ad esempio URL)
    d.exclude    Una lista di indici di attributi da escludere da d.inputs.
                 Gli elementi di questa lista devono essere interi (indici) o nomi di attributi.
    Solitamente, per costruire il dataset e' sufficiente invocare il costruttore passando gli opportuni argomenti;
    per usare il dataset e' sufficiente accedere alle variabili d.examples, d.inputs e d.target."""

    def __init__(self, examples=None, attrs=None, attrnames=None, target=-1,
                 inputs=None, values=None, distance=mean_boolean_error,
                 name='', source='', exclude=(), directory_dataset = "datasets" ):
        """Costruttore. Accetta come argomenti tutti i campi del dataset.
        Gli esempi possono anche essere una stringa o un file .csv da cui parsare gli esempi
        (automaticamente) usando la funzione parse_csv.
        """
        self.name = name
        self.source = source
        self.values = values
        self.distance = distance
        if values is None:
            self.got_values_flag = False
        else:
            self.got_values_flag = True

        # Inizializza .examples da una stringa o da una lista o da un file
        if isinstance(examples, str):
            self.examples = parse_csv(examples) #in questo caso parsa la stringa in input
        elif examples is None:
            #in questo caso parsa il file .csv
            self.examples = parse_csv(DataFile(name + '.csv', location_dir = directory_dataset).read())
        else:
            #altrimenti gli esempi vengono presi dall'argomento
            self.examples = examples
        # Attrs sono gli indici degli esempi
        if attrs is None and self.examples is not None:
            attrs = list(range(len(self.examples[0])))
        self.attrs = attrs
        # Inizializza attrnames da una stringa, lista o con valori di default
        if isinstance(attrnames, str):
            self.attrnames = attrnames.split()
        else:
            self.attrnames = attrnames or attrs
        self.setproblem(target, inputs=inputs, exclude=exclude)

    def setproblem(self, target, inputs=None, exclude=()):
        """Set (or change) the target and/or inputs.
        This way, one DataSet can be used multiple ways. inputs, if specified,
        is a list of attributes, or specify exclude as a list of attributes
        to not use in inputs. Attributes can be -n .. n, or an attrname.
        Also computes the list of possible values, if that wasn't done yet."""
        self.target = self.attrnum(target)
        exclude = list(map(self.attrnum, exclude))
        if inputs:
            self.inputs = removeall(self.target, inputs)
        else:
            self.inputs = [a for a in self.attrs
                           if a != self.target and a not in exclude]
        if not self.values:
            self.update_values()
        self.check_me()

    def check_me(self):
        """Check that my fields make sense."""
        assert len(self.attrnames) == len(self.attrs)
        assert self.target in self.attrs
        assert self.target not in self.inputs
        assert set(self.inputs).issubset(set(self.attrs))
        if self.got_values_flag:
            # only check if values are provided while initializing DataSet
            list(map(self.check_example, self.examples))

    def add_example(self, example):
        """Add an example to the list of examples, checking it first."""
        self.check_example(example)
        self.examples.append(example)

    def check_example(self, example):
        """Raise ValueError if example has any invalid values."""
        if self.values:
            for a in self.attrs:
                if example[a] not in self.values[a]:
                    raise ValueError('Bad value {} for attribute {} in {}'
                                     .format(example[a], self.attrnames[a], example))

    def attrnum(self, attr):
        """Returns the number used for attr, which can be a name, or -n .. n-1."""
        if isinstance(attr, str):
            return self.attrnames.index(attr)
        elif attr < 0:
            return len(self.attrs) + attr
        else:
            return attr

    def update_values(self):
        self.values = list(map(unique, zip(*self.examples)))

    def sanitize(self, example):
        """Return a copy of example, with non-input attributes replaced by None."""
        return [attr_i if i in self.inputs else None
                for i, attr_i in enumerate(example)]

    def sanitize_v2(self, example):
        """Return a copy of example, with non-input attributes eliminated."""
        return [attr_i if i in self.inputs else None
                for i, attr_i in enumerate(example)]

    def classes_to_numbers(self, classes=None):
        """Converts class names to numbers."""
        if not classes:
            # If classes were not given, extract them from values
            classes = sorted(self.values[self.target])
        for item in self.examples:
            item[self.target] = classes.index(item[self.target])

    def remove_examples(self, value=""):
        """Remove examples that contain given value."""
        self.examples = [x for x in self.examples if value not in x]
        self.update_values()

    def split_values_by_classes(self):
        """Split values into buckets according to their class."""
        buckets = defaultdict(lambda: [])
        target_names = self.values[self.target]

        for v in self.examples:
            item = [a for a in v if a not in target_names] # Remove target from item
            buckets[v[self.target]].append(item) # Add item to bucket of its class

        return buckets

    def find_means_and_deviations(self):
        """Finds the means and standard deviations of self.dataset.
        means     : A dictionary for each class/target. Holds a list of the means
                    of the features for the class.
        deviations: A dictionary for each class/target. Holds a list of the sample
                    standard deviations of the features for the class."""
        target_names = self.values[self.target]
        feature_numbers = len(self.inputs)

        item_buckets = self.split_values_by_classes()
        
        means = defaultdict(lambda: [0 for i in range(feature_numbers)])
        deviations = defaultdict(lambda: [0 for i in range(feature_numbers)])

        for t in target_names:
            # Find all the item feature values for item in class t
            features = [[] for i in range(feature_numbers)]
            for item in item_buckets[t]:
                features = [features[i] + [item[i]] for i in range(feature_numbers)]

            # Calculate means and deviations fo the class
            for i in range(feature_numbers):
                means[t][i] = mean(features[i])
                deviations[t][i] = stdev(features[i])

        return means, deviations


    def __repr__(self):
        return '<DataSet({}): {:d} examples, {:d} attributes>'.format(
            self.name, len(self.examples), len(self.attrs))


# ______________________________________________________________________________


def parse_csv(input, delim=','):
    """Esegue il parsing di una stringa in formato comma-separated values,
    in cui ogni riga ha campi separati da virgole.
    Questi dati vengono convertiti in una lista di liste, saltando le righe vuote.
    Esempio:
    >>> parse_csv('1, 2, 3 \n 0, 2, na')
    [[1, 2, 3], [0, 2, 'na']]"""
    lines = [line for line in input.splitlines() if line.strip()]
    return [list(map(num_or_str, line.split(delim))) for line in lines]

# ______________________________________________________________________________


# ______________________________________________________________________________
# MODELLO LINEARE

def LinearLearner(dataset, learning_rate=0.01, epochs=100):
    """Addestra un modello lineare.
       In input richiede:
        dataset: oggetto della classe DataSet che contiene gli esempi
                 per il training e le funzioni per la gestione del data set
        learning_rate: eta a lezione (default 0.01)
        epochs: numero massimo di epoche di training (default 100)
       In output restituisce una lista contenente (nell'ordine):
        il predittore (per uso funzione predict, uso post training)
        i parametri addestrati (w)
        l'errore quadratico medio (MSE) di training al variare delle epoche
    """
    idx_i = dataset.inputs # indici delle variabili di input
    #nota: idx_i e' una lista degli indici nei dati di ogni esempio
    #   come [0,1,2,3], o [2,5,7]
    idx_t = dataset.target # indice della variabile usata per target 
                           #(variabile target y a lezione)
    examples = dataset.examples #lista di tutti gli esempi
    #nota: ogni esempio corrisponde a una lista contenente sia le variabili
    # di input sia il target
    num_examples = len(examples) # numero di esempi nel dataset (l a lezione)
    input_dim = len(idx_i) # dimensione dell'input (n a lezione)
    #inizializza i pesi del modello in modo casuale
    #da una distribuzione uniforme tra -0.5 e 0.5
    w = [random.uniform(-0.5, 0.5) for _ in range(len(idx_i) + 1)]

    trainingError = [] # inizializza la lista che conterra' l'MSE ad ogni epoca di tr.
    for epoch in range(epochs):
        err = []
        inputs = [] #conterra' l'input di ogni esempio nel dataset
        #considera tutti gli esempi, prepara il calcolo gradiente
        for example in examples:
            x = [1] + [example[i] for i in idx_i] #input (lista locale dei
                                                  #valori delle variabili di input)
                                                  #e aggiunge l'input bias unitario
            inputs.append(x) #aggiunge l'esempio attuale alla lista 
            out = dotproduct(w, x) #output y del modello
            t = example[idx_t] #target (fu y a lezione)
            err.append(t - out) 
        for i in range(len(w)): #calcolo il DeltaW
            delta_wi = 0
            for p in range(num_examples):
                x_pi = inputs[p][i] #componente i del pattern p
                delta_wi += err[p] * x_pi
            #stiamo tenendo la costante 2 come a lezione,
            #ma dividendo per il numero di esempi (l) , ossia una LMS
            delta_wi = 2 * (delta_wi /num_examples)
            w[i] = w[i] + learning_rate * delta_wi
        # calcola l'MSE per questa epoca e lo aggiunge alla lista
        trainingError.append(sum(e*e for e in err)/len(err))

    def predict(example):
        # funzione usata per calcolare l'output del modello
        # per l'esempio specificato in input
        x = [1] + example
        return dotproduct(w, x)

    return predict, w, trainingError

# ______________________________________________________________________________
# DECISION TREE

def DecisionTreeLearner(dataset):
    """Addestra un albero di decisione.
       In input richiede:
        dataset: oggetto della classe DataSet che contiene gli esempi
                 per il training e le funzioni per la gestione del data set
       In output restituisce il predittore
       (per uso post training)
    """

    target, values = dataset.target, dataset.values

    def decision_tree_learning(examples, attrs, parent_examples=()):
        if len(examples) == 0:
            return plurality_value(parent_examples)
        elif all_same_class(examples):
            return DecisionLeaf(examples[0][target])
        elif len(attrs) == 0:
            return plurality_value(examples)
        else:
            A = choose_attribute(attrs, examples)
            tree = DecisionFork(A, dataset.attrnames[A])
            for (v_k, exs) in split_by(A, examples):
                subtree = decision_tree_learning(
                    exs, removeall(A, attrs), examples)
                tree.add(v_k, subtree)
            return tree

    def plurality_value(examples):
        """Return the most popular target value for this set of examples.
        (If target is binary, this is the majority; otherwise plurality.)"""
        popular = argmax_random_tie(values[target],
                                    key=lambda v: count(target, v, examples))
        return DecisionLeaf(popular)

    def count(attr, val, examples):
        """Count the number of examples that have attr = val."""
        return sum(e[attr] == val for e in examples)

    def all_same_class(examples):
        """Are all these examples in the same target class?"""
        class0 = examples[0][target]
        return all(e[target] == class0 for e in examples)

    def choose_attribute(attrs, examples):
        """Choose the attribute with the highest information gain."""
        return argmax_random_tie(attrs,
                                 key=lambda a: information_gain(a, examples))

    def information_gain(attr, examples):
        """Return the expected reduction in entropy from splitting by attr."""
        def I(examples):
            return information_content([count(target, v, examples)
                                        for v in values[target]])
        N = float(len(examples))
        remainder = sum((len(examples_i) / N) * I(examples_i)
                        for (v, examples_i) in split_by(attr, examples))
        return I(examples) - remainder

    def split_by(attr, examples):
        """Return a list of (val, examples) pairs for each val of attr."""
        return [(v, [e for e in examples if e[attr] == v])
                for v in values[attr]]

    return decision_tree_learning(dataset.examples, dataset.inputs)


def information_content(values):
    """Number of bits to represent the probability distribution in values."""
    probabilities = normalize(removeall(0, values))
    return sum(-p * math.log2(p) for p in probabilities)

class DecisionFork:
    """A fork of a decision tree holds an attribute to test, and a dict
    of branches, one for each of the attribute's values."""

    def __init__(self, attr, attrname=None, branches=None):
        """Initialize by saying what attribute this node tests."""
        self.attr = attr
        self.attrname = attrname or attr
        self.branches = branches or {}

    def __call__(self, example):
        """Given an example, classify it using the attribute and the branches."""
        attrvalue = example[self.attr]
        return self.branches[attrvalue](example)

    def add(self, val, subtree):
        """Add a branch.  If self.attr = val, go to the given subtree."""
        self.branches[val] = subtree

    def display(self, indent=0):
        name = self.attrname
        print('Test', name)
        for (val, subtree) in self.branches.items():
            print(' ' * 4 * indent, name, '=', val, '==>', end=' ')
            subtree.display(indent + 1)

    def __repr__(self):
        return ('DecisionFork({0!r}, {1!r}, {2!r})'
                .format(self.attr, self.attrname, self.branches))


class DecisionLeaf:
    """A leaf of a decision tree holds just a result."""

    def __init__(self, result):
        self.result = result

    def __call__(self, example):
        return self.result

    def display(self, indent=0):
        print('RESULT =', self.result)

    def __repr__(self):
        return repr(self.result)

# ______________________________________________________________________________
# Nearest Neighbor

def NearestNeighborClassifier(dataset, k=1):
    """Implementazione dell’algoritmo k-NN per problemi di classificazione binaria.
       In input richiede:
        . il dadataset, oggetto di classe DataSet
        . il numero di vicini da considerare
       In output restituisce il predittore.
       Nota: l’output del predittore è calcolato come la media
             del target dei k elementi piu’ vicini nel dataset.
             Quindi l’output e’ la classe piu’ frequente tra i k vicini."""
    def predict(example):
        """Cerca i k elementi più vicini ad example e calcola la media
           dell’output."""
        # heapq.nsmallest restituisce una lista degli 'n' elementi
        # piu' vicini ad example, usando come distanza
        # la funzione dataset.distance, che di default e'
        # la distanza Euclidea.
        neighbors = heapq.nsmallest(k,((dataset.distance(e,example), e) for e in dataset.examples))

        # restituisce il valore target maggiormente presente in neighbors
        # discretizzando la media dei valori degli elementi piu’ vicini 
        return (mean(e[dataset.target] for (d, e) in neighbors)>0.5)
    return predict

def NearestNeighborClassifier_cat(dataset, k=1):
    """Implementazione dell’algoritmo k-NN per problemi di classificazione con etichetta
       categorica.
       In input richiede:
        . il dadataset, oggetto di classe DataSet
        . il numero di vicini da considerare
       In output restituisce il predittore.
       Nota: l’output del predittore è calcolato come la moda
             del target dei k elementi piu’ vicini nel dataset.
             Quindi l’output e’ l’etichetta (categorica) 
             della classe piu’ frequente tra i k vicini."""

    def predict(example):
        """Cerca i k elementi più vicini ad example e applica un majority vote
           sull’output."""
        # heapq.nsmallest restituisce una lista degli 'n' elementi
        # piu' vicini ad example, usando come distanza
        # la funzione dataset.distance, che di default e'
        # la distanza Euclidea.
        neighbors = heapq.nsmallest(k,((dataset.distance(e,example), e) for e in dataset.examples))

        # mode restituisce il valore target maggiormente presente in neighbors
        return mode(e[dataset.target] for (d, e) in neighbors)
    return predict

def NearestNeighborRegressor(dataset, k=1):
    """Implementazione dell’algoritmo k-NN per problemi di regressione.
       In input richiede:
        . il dadataset, oggetto di classe DataSet
        . il numero di vicini da considerare
       In output restituisce il predittore.
       Nota: l’output del predittore è calcolato come la media
             del target dei k elementi piu’ vicini nel dataset.."""
    def predict(example):
        """Cerca i k elementi più vicini ad example e calcola la media
           dell’output."""
        # heapq.nsmallest restituisce una lista degli 'n' elementi
        # piu' vicini ad example, usando come distanza
        # la funzione dataset.distance, che di default e'
        # la distanza Euclidea.
        neighbors = heapq.nsmallest(k, ((dataset.distance(e, example), e)
                                   for e in dataset.examples))

        # calcola l’output come la media dell’output dei vicini
        return mean(e[dataset.target] for (d, e) in neighbors)
    return predict





# ______________________________________________________________________________
# Funzioni per valutare la performance dei modelli


def err_ratio(predict, dataset, examples=None, verbose=0):
    """Calcola la proporzione di esempi che non sono correttamente predetti.
       Nota: in questa funzione, gli esempi sono considerati correttamente
       predetti se hanno lo stesso valore del target corrispondente.
       E' una funzione di loss adatta al caso di classificazione
       con label categoriche.

       Il parametro verbose in input e' usato per visualizzare gli output corretti
       e quelli errati, secondo il seguente schema:
       verbose - 0: No output; 1: Output wrong; 2 (or greater): Output correct"""
    # questa versione e' piu' idonea ad esempio per il caso di dati categorici
    if examples is None:
        examples = dataset.examples
    if len(examples) == 0:
        return 0.0
    right = 0.0
    for example in examples:
        desired = example[dataset.target]
        output = predict(dataset.sanitize(example))
        if output == desired:
            right += 1
            if verbose >= 2:
                print('   OK: got {} for {}'.format(desired, example))
        elif verbose:
            print('WRONG: got {}, expected {} for {}'.format(
                output, desired, example))
    return 1 - (right / len(examples))



def accuracy_binary(predict, dataset, examples=None, verbose=0):
    """Calcola l'accuratezza di un predittore per un problema di classificazione binaria
       (cioe' la proporzione degli esempi correttamente classificati).
       Nota: questa funzione assume che
        - il target e' un numero in {0,1}, dove
          0 corrisponde alla classe negativa
          1 corrisponde alla classe positiva
        - l'output e' un numero reale, che viene discretizzato in {0,1}
          usando 0.5 come valore soglia.

       Il parametro verbose in input e' usato per visualizzare gli output corretti
       e quelli errati, secondo il seguente schema:
       verbose - 0: No output; 1: Output wrong; 2 (or greater): Output correct"""

    if examples is None:
        examples = dataset.examples
    if len(examples) == 0:
        return 0.0
    right = 0.0
    for example in examples:
        desired = example[dataset.target]
        output = predict(dataset.sanitize(example))
        if output > 0.5:
            output = 1
        else:
            output = 0
                
        if output == desired:
            right += 1
            if verbose >= 2:
                print('   OK: got {} for {}'.format(desired, example))
        elif verbose:
            print('WRONG: got {}, expected {} for {}'.format(
                output, desired, example))
    return (right / len(examples))

def misclassification_loss(predict, dataset, examples=None, verbose=0):
    """Restituisce l'errore di classificazione per un problema di classificazione binaria
       (cioe' la proporzione degli esempi non correttamente classificati).

       Nota: questa funzione assume che
        - il target e' un numero in {0,1}, dove
          0 corrisponde alla classe negativa
          1 corrisponde alla classe positiva
        - l'output e' un numero reale, che viene discretizzato in {0,1}
          usando 0.5 come valore soglia.

       Il parametro verbose in input e' usato per visualizzare gli output corretti
       e quelli errati, secondo il seguente schema:
       verbose - 0: No output; 1: Output wrong; 2 (or greater): Output correct"""    
    return 1-accuracy_binary(predict, dataset, examples, verbose)



def mse_loss(predict, dataset, examples=None, verbose=0): 
    """ Restituisce l'errore quadratico medio per un problema di regressione."""

    if examples is None:
        examples = dataset.examples
    if len(examples) == 0:
        return float("inf")
    #right = 0.0
    err = 0
    for example in examples:
        desired = example[dataset.target]
        #remove nones
        ex = dataset.sanitize(example)
        ex_c = [x for x in ex if x is not None]
        output = predict(ex_c) #dataset.sanitize(example))
        err = err + (desired-output)**2
        
    return (err / len(examples))



def train_and_test(dataset, start, end):
    """ Funzione usata per separare gli esempi nel dataset in training e test sets.
        I parametri start ed end identificano la porzione del dataset da usare in test,
        il resto del dataset viene usato per il training set."""
    start = int(start)
    end = int(end)
    examples = dataset.examples
    train = examples[:start] + examples[end:]
    val = examples[start:end]
    return train, val


def cross_validation(learner, dataset, loss, k=10, learning_rate = None, epochs = None, K = None):
    """ Funzione usata per valutare la performance del modello di apprendimento
        secondo uno schema di k-fold cross validation.
        Usando la loss function indicata come parametro, la funzione calcola e
        restituisce in output la performance del learner in training e validation,
        mediata sulle k fold. """
    fold_errT = 0
    fold_errV = 0
    n = len(dataset.examples)
    examples = dataset.examples
    for fold in range(k):
        random.shuffle(dataset.examples)
        train_data, val_data = train_and_test(dataset, fold * (n / k),
                                                  (fold + 1) * (n / k))
        dataset.examples = train_data
        if (learning_rate is not None) and (epochs is not None):
            # linear learner
            h = learner(dataset, learning_rate,epochs)
        elif (K is not None):
            # k-NN
            h = learner(dataset, k)
        else:
            # other cases
            h = learner(dataset)
        if callable(h) == False:
            fold_errT += loss(h[0], dataset, train_data)
            fold_errV += loss(h[0], dataset, val_data)
        else:
            fold_errT += loss(h, dataset, train_data)
            fold_errV += loss(h, dataset, val_data)
        # Reverting back to original once test is completed
        dataset.examples = examples
    return fold_errT / k, fold_errV / k


def hold_out(learner, dataset, loss, propTr = 0.75, propVl = 0.25, learning_rate = None, epochs = None, K = None):
    """ Funzione usata per valutare la performance del modello di apprendimento
        secondo uno schema di hold-out cross validation.
        Il dataset in ingresso viene separato in training, validation e test sets:
        - il training set contiene il propTr*100 % dei dati del dataset
        - il test set contiene gli esempi del dataset che non sono nel training set
        - il validation set contiene il propVl*100% dei dati del training set.
        Nota che:
        - a lezione si e' definito il design set come l'unione di training set e validation set;
        - qui e' stato chiamato training set quello che a lezione e' stato definito come design set;
        - qui propVl indica la proporzione di dati del design set (come definito a lezione) che sono usati
        per il validation set;
        - qui propTr indica la proporzione dei dati nell'intero dataset che sono usati per il design set (come definito a lezione).
        
        In output restituisce la performance del modello (in termini dell'argomento loss)
        sui dati di training, validation e test. """
    
    fold_errT = 0
    fold_errV = 0
    n = len(dataset.examples)
    random.shuffle(dataset.examples)
    examples = dataset.examples
    #calcola gli indici degli esempi di training, validation e test
    trInd = range(0,math.floor(propTr*len(examples)))
    tsInd = range(trInd[-1]+1,trInd[-1]+len(examples)-len(trInd))
    valInd = range(0,math.floor(propVl * len(trInd)))
    val_trInd = range(valInd[-1]+1,valInd[-1]+len(trInd)-len(valInd))
        
    train_data = [examples[i] for i in trInd]
    val_data = [examples[i] for i in valInd]
    val_tr_data = [examples[i] for i in val_trInd]
    ts_data = [examples[i] for i in tsInd]
    # --- validation
    # addestra sul training set
    dataset.examples = val_tr_data
    
    if (learning_rate is not None) and (epochs is not None):
        # linear learner
        h = learner(dataset, learning_rate,epochs)
    elif (K is not None):
        # k-NN
        h = learner(dataset,K)
    else:
        # other cases
        h = learner(dataset)
    if callable(h) == False:
        valTr_err = loss(h[0],dataset,val_tr_data)
        val_err = loss(h[0],dataset,val_data)
    else:
        valTr_err = loss(h,dataset,val_tr_data)
        val_err = loss(h,dataset,val_data)

        # --- training & test
    dataset.examples = train_data
    
    if (learning_rate is not None) and (epochs is not None):
        h = learner(dataset, learning_rate,epochs)
    else:
        h = learner(dataset)
    if callable(h) == False:
        tr_err = loss(h[0],dataset,train_data)
        ts_err = loss(h[0],dataset,ts_data)
    else:
        tr_err = loss(h,dataset,train_data)
        ts_err = loss(h,dataset,ts_data)

    # restituisce loss su training, validation e test
    # per tutti i modelli
    if callable(h) == False:
        # nel caso del modello lineare aggiunge anche il vettore dei pesi w
        # e la lista degli errori quadratici medi durante le epoche
        return tr_err, val_err, ts_err, h[1], h[2]
    else:
        return tr_err, val_err, ts_err
