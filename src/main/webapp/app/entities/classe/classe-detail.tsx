import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './classe.reducer';
import { IClasse } from 'app/shared/model/classe.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClasseDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClasseDetail = (props: IClasseDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { classeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="intranetApp.classe.detail.title">Classe</Translate> [<b>{classeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="idClasse">
              <Translate contentKey="intranetApp.classe.idClasse">Id Classe</Translate>
            </span>
          </dt>
          <dd>{classeEntity.idClasse}</dd>
          <dt>
            <span id="nomClasse">
              <Translate contentKey="intranetApp.classe.nomClasse">Nom Classe</Translate>
            </span>
          </dt>
          <dd>{classeEntity.nomClasse}</dd>
          <dt>
            <span id="niveau">
              <Translate contentKey="intranetApp.classe.niveau">Niveau</Translate>
            </span>
          </dt>
          <dd>{classeEntity.niveau}</dd>
          <dt>
            <Translate contentKey="intranetApp.classe.filiere">Filiere</Translate>
          </dt>
          <dd>{classeEntity.filiere ? classeEntity.filiere.idFiliere : ''}</dd>
        </dl>
        <Button tag={Link} to="/classe" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/classe/${classeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ classe }: IRootState) => ({
  classeEntity: classe.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClasseDetail);
