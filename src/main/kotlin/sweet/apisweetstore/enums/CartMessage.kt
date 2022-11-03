package sweet.apisweetstore.enums

enum class CartMessage(val message: String) {

    //MENSAGENS PARA QUANDO FOR ADICIONAR AO CARRINHO
    EMPTY_LIST_CARD(message = "Erro ao cadastrar no carrinho, deve ser passada na requisição lista com pelo menos um item"),
    SHOULD_BIG_THAN_0(message = "Erro ao cadastrar itens, quantidades de todos produtos devem ser maiores que zero"),
    CAN_SEND_JUST_ONE_COMPANY(message = "Pode ter apenas uma empresa por usuario no carrinho"),
    SEND_JUST_DIFFERENT_PRODUCTS(message = "Não é possivel enviar produtos repetidos"),
    ERROR_SOMEONE_ITEM(message = "Ocorreu um erro ao cadastrar um ou mais itens no carrinho"),
    ALL_ITENS_SAVED(message = "Todos itens salvos com sucesso no carrinho"),

    //GENERICO
    USER_NOT_EXIST(message = "Usuário não existe, items não cadastrados ao carrinho"),

    //GET DO CARRINHO
    EMPTY_CART(message = "Carrinho vazio!")

}